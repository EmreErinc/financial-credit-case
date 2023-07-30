package com.colendi.financial.credit.domain;

import com.colendi.financial.credit.domain.entity.InstallmentEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InstallmentMapper {

  public Function<InstallmentEntity, InstallmentDetail> mapToInstallmentDetail() {
    return installmentEntity ->
        InstallmentDetail.builder()
            .installmentId(installmentEntity.getId())
            .dueDate(installmentEntity.getDueDate().toLocalDateTime().toLocalDate())
            .amount(installmentEntity.getAmount())
            .status(installmentEntity.getStatus())
            .paidDate(installmentEntity.getPaidDate() == null ? null : installmentEntity.getPaidDate().toLocalDateTime().toLocalDate())
            .build();
  }
}
