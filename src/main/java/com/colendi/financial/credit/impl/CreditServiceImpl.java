package com.colendi.financial.credit.impl;

import com.colendi.financial.commons.model.DoneResponse;
import com.colendi.financial.credit.CreditService;
import com.colendi.financial.credit.api.model.request.LoanCreditRequest;
import com.colendi.financial.credit.api.model.request.PayInstallmentRequest;
import com.colendi.financial.credit.api.model.request.UpdateCreditStatusRequest;
import com.colendi.financial.credit.api.model.response.CreditDetailResponse;
import com.colendi.financial.credit.api.model.response.CreditListResponse;
import com.colendi.financial.credit.api.model.response.CreditResponse;
import com.colendi.financial.credit.domain.*;
import com.colendi.financial.credit.domain.entity.CreditEntity;
import com.colendi.financial.credit.domain.entity.InstallmentEntity;
import com.colendi.financial.credit.domain.repository.CreditRepository;
import com.colendi.financial.credit.domain.repository.InstallmentRepository;
import com.colendi.financial.user.UserService;
import com.colendi.financial.user.api.model.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

  private static final int ONE_MONTH_AS_DAY = 30;
  private static final double INTEREST_RATE = 2.32;

  private final CreditRepository creditRepository;
  private final InstallmentRepository installmentRepository;
  private final UserService userService;

  private final CreditMapper creditMapper;
  private final InstallmentMapper installmentMapper;

  private static LocalDateTime calculateDueDate(LocalDateTime now, int i) {
    LocalDateTime dueDate = now.plusDays((long) ONE_MONTH_AS_DAY * i);

    if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
      dueDate = dueDate.plusDays(2);
    } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
      dueDate = dueDate.plusDays(1);
    }
    return dueDate;
  }

  private static void validateBeforePayment(CreditEntity creditEntity) {
    if (CreditStatus.REQUESTED.equals(creditEntity.getStatus())) {
      throw new RuntimeException("Credit not approved yet");
    }

    if (CreditStatus.REJECTED.equals(creditEntity.getStatus())) {
      throw new RuntimeException("Credit is rejected. You can not pay installment");
    }
  }

  @Override
  public DoneResponse loanCredit(LoanCreditRequest request) {
    UserDetailResponse requesterUser = userService.getUserDetail(request.getUserId()); // check user exists

    CreditEntity savedCreditEntity = creditRepository.save(CreditEntity.builder()
        .userId(request.getUserId())
        .amount(request.getAmount())
        .installmentCount(request.getInstallmentCount())
        .status(CreditStatus.REQUESTED)
        .build());

    if (request.getInstallmentCount() != 1) {
      persistInstallments(request, savedCreditEntity);
    } else {
      persistSingleInstallment(request, savedCreditEntity);
    }

    return DoneResponse.success();
  }

  private void persistSingleInstallment(LoanCreditRequest request, CreditEntity savedCreditEntity) {
    installmentRepository.save(InstallmentEntity.builder()
        .creditId(savedCreditEntity.getId())
        .amount(request.getAmount())
        .dueDate(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
        .status(InstallmentStatus.PENDING)
        .build());
  }

  private void persistInstallments(LoanCreditRequest request, CreditEntity savedCreditEntity) {
    BigDecimal amountPerInstallment = request.getAmount().divide(BigDecimal.valueOf(request.getInstallmentCount()));

    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

    for (int i = 1; i <= request.getInstallmentCount(); i++) {
      LocalDateTime dueDate = calculateDueDate(now, i);

      installmentRepository.save(InstallmentEntity.builder()
          .creditId(savedCreditEntity.getId())
          .amount(amountPerInstallment)
          .dueDate(Timestamp.from(dueDate.toInstant(ZoneOffset.UTC)))
          .status(InstallmentStatus.PENDING)
          .build());
    }
  }

  @Override
  public CreditDetailResponse getCreditDetail(long creditId) {
    CreditEntity creditEntity = creditRepository.findById(creditId)
        .orElseThrow(() -> new RuntimeException("Credit not found"));

    List<InstallmentDetail> installments = installmentRepository.findByCreditId(creditId)
        .stream()
        .map(installmentMapper.mapToInstallmentDetail())
        .sorted(Comparator.comparing(InstallmentDetail::getStatus).reversed())
        .toList();

    return CreditDetailResponse.builder()
        .creditId(creditEntity.getId())
        .amount(creditEntity.getAmount())
        .status(creditEntity.getStatus())
        .creationDate(creditEntity.getCreatedAt().toLocalDateTime())
        .installments(installments)
        .build();
  }

  @Override
  public CreditListResponse getCreditList(long userId, int page, int size, CreditStatus status, LocalDate createdAt) {
    List<CreditResponse> creditList = creditRepository.findByUserId(userId, page, size, defineAvailableStatuses(status), defineCreatedAt(createdAt))
        .stream()
        .map(creditMapper.mapToCreditResponse())
        .toList();

    return CreditListResponse.builder()
        .credits(creditList)
        .build();
  }

  private Set<CreditStatus> defineAvailableStatuses(CreditStatus status) {
    Set<CreditStatus> desiredStatuses = new HashSet<>();

    if (status != null) {
      desiredStatuses.add(status);
    } else {
      desiredStatuses = Set.of(CreditStatus.values());
    }

    return desiredStatuses;
  }

  private Timestamp defineCreatedAt(LocalDate createdAt) {
    return Timestamp.valueOf(LocalDateTime.of(createdAt == null ? LocalDate.MIN : createdAt, LocalTime.MIN));
  }

  @Override
  public DoneResponse payInstallment(PayInstallmentRequest request) {
    InstallmentEntity installmentEntity = installmentRepository.findById(request.getInstallmentId())
        .orElseThrow(() -> new RuntimeException("Installment not found"));

    CreditEntity creditEntity = creditRepository.findById(installmentEntity.getCreditId())
        .orElseThrow(() -> new RuntimeException("Credit not found"));

    validateBeforePayment(creditEntity);

    switch (installmentEntity.getStatus()) {
      case PENDING -> handlePendingStatus(request, installmentEntity);
      case PARTIALLY_PAID -> handlePartiallyPaidStatus(request, installmentEntity);
      case OVERDUE -> handleOverdueStatus(request, installmentEntity);
      default -> throw new RuntimeException("Installment is already paid");
    }

    checkCreditCompleteness(installmentEntity, creditEntity);

    return DoneResponse.success();
  }

  private void handleOverdueStatus(PayInstallmentRequest request, InstallmentEntity installmentEntity) {
    if (installmentEntity.getAmount().add(installmentEntity.getInterestAmount()).equals(request.getAmount())) {
      installmentEntity.setStatus(InstallmentStatus.PAID);
    } else if (installmentEntity.getAmount().add(installmentEntity.getInterestAmount()).compareTo(request.getAmount()) > 0) {
      installmentEntity.setStatus(InstallmentStatus.PARTIALLY_PAID);
    } else {
      throw new RuntimeException("Paid amount can not be greater than installment amount");
    }

    installmentEntity.setPaidAmount(request.getAmount());
    installmentEntity.setPaidDate(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

    installmentRepository.save(installmentEntity);
  }

  private void handlePartiallyPaidStatus(PayInstallmentRequest request, InstallmentEntity installmentEntity) {
    BigDecimal paidAmount = installmentEntity.getPaidAmount().add(request.getAmount());

    if (installmentEntity.getAmount().equals(paidAmount)) {
      installmentEntity.setStatus(InstallmentStatus.PAID);
    } else if (installmentEntity.getAmount().compareTo(paidAmount) > 0) {
      installmentEntity.setStatus(InstallmentStatus.PARTIALLY_PAID);
    } else {
      throw new RuntimeException("Paid amount can not be greater than installment amount");
    }

    installmentEntity.setPaidAmount(paidAmount);
    installmentEntity.setPaidDate(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

    installmentRepository.save(installmentEntity);
  }

  private void handlePendingStatus(PayInstallmentRequest request, InstallmentEntity installmentEntity) {
    if (installmentEntity.getAmount().equals(request.getAmount())) {
      installmentEntity.setStatus(InstallmentStatus.PAID);
    } else if (installmentEntity.getAmount().compareTo(request.getAmount()) > 0) {
      installmentEntity.setStatus(InstallmentStatus.PARTIALLY_PAID);
    } else {
      throw new RuntimeException("Paid amount can not be greater than installment amount");
    }

    installmentEntity.setPaidAmount(request.getAmount());
    installmentEntity.setPaidDate(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

    installmentRepository.save(installmentEntity);
  }

  private void checkCreditCompleteness(InstallmentEntity installmentEntity, CreditEntity creditEntity) {
    Long paidInstallmentCount = installmentRepository.countByCreditIdAndStatus(installmentEntity.getCreditId(), InstallmentStatus.PAID);

    if (paidInstallmentCount == creditEntity.getInstallmentCount()) {
      creditEntity.setStatus(CreditStatus.COMPLETED);
      creditRepository.save(creditEntity);
    }
  }

  @Override
  public DoneResponse updateCreditStatus(long creditId, UpdateCreditStatusRequest request) {
    CreditEntity creditEntity = creditRepository.findById(creditId)
        .orElseThrow(() -> new RuntimeException("Credit not found"));

    creditEntity.setStatus(request.getUpdatedStatus());
    creditEntity.setUpdatedAt(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
    creditRepository.save(creditEntity);

    return DoneResponse.success();
  }

  @Override
  public void calculateLatePaymentInterests() {
    installmentRepository.findByDueDateIsAfterAndStatus(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)), InstallmentStatus.PENDING)
        .forEach(installmentEntity -> {
          BigDecimal overDueDays = BigDecimal.valueOf(Duration.between(installmentEntity.getDueDate().toLocalDateTime(), LocalDateTime.now(ZoneOffset.UTC)).toDays());
          BigDecimal interestRateMultiplier = BigDecimal.valueOf(INTEREST_RATE).divide(BigDecimal.valueOf(100L));

          BigDecimal latePaymentInterest = installmentEntity.getAmount()
              .multiply(overDueDays)
              .multiply(interestRateMultiplier)
              .divide(BigDecimal.valueOf(360L));

          installmentEntity.setInterestAmount(installmentEntity.getAmount().add(latePaymentInterest));
          installmentEntity.setInterestDayCount(overDueDays.intValue());
          installmentEntity.setStatus(InstallmentStatus.OVERDUE);
          installmentRepository.save(installmentEntity);
        });
  }
}
