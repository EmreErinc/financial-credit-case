package com.colendi.financial.credit.api.model.response;

import com.colendi.financial.credit.domain.CreditStatus;
import com.colendi.financial.credit.domain.InstallmentDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreditDetailResponse {

  long creditId;
  BigDecimal amount;
  CreditStatus status;
  LocalDateTime creationDate;
  List<InstallmentDetail> installments;
}
