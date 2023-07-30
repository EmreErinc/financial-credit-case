package com.colendi.financial.credit.impl;

import com.colendi.financial.commons.DoneResponse;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

  private static final int ONE_MONTH_AS_DAY = 30;

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

    return DoneResponse.success();
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
  public CreditListResponse getCreditList(long userId, int page, int size) {
    List<CreditResponse> creditList = creditRepository.findByUserId(userId, PageRequest.of(page - 1, size))
        .stream()
        .map(creditMapper.mapToCreditResponse())
        .toList();

    return CreditListResponse.builder()
        .credits(creditList)
        .build();
  }

  @Override
  public DoneResponse payInstallment(PayInstallmentRequest request) {
    InstallmentEntity installmentEntity = installmentRepository.findById(request.getInstallmentId())
        .orElseThrow(() -> new RuntimeException("Installment not found"));

    CreditEntity creditEntity = creditRepository.findById(installmentEntity.getCreditId())
        .orElseThrow(() -> new RuntimeException("Credit not found"));

    if (CreditStatus.REQUESTED.equals(creditEntity.getStatus())) {
      throw new RuntimeException("Credit not approved yet");
    }

    if (CreditStatus.REJECTED.equals(creditEntity.getStatus())) {
      throw new RuntimeException("Credit is rejected. You can not pay installment");
    }

    // todo check installment user relation
    // todo partial payment may be implemented

    if (installmentEntity.getAmount().equals(request.getAmount())) {
      installmentEntity.setStatus(InstallmentStatus.PAID);
      installmentEntity.setPaidDate(Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

      installmentRepository.save(installmentEntity);
    }

    Long paidInstallmentCount = installmentRepository.countByCreditIdAndStatus(installmentEntity.getCreditId(), InstallmentStatus.PAID);

    if (paidInstallmentCount == creditEntity.getInstallmentCount()) {
      creditEntity.setStatus(CreditStatus.COMPLETED);
      creditRepository.save(creditEntity);
    }

    return DoneResponse.success();
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
}
