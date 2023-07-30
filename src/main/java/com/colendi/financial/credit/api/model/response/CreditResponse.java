package com.colendi.financial.credit.api.model.response;

import com.colendi.financial.credit.domain.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CreditResponse {

  long creditId;
  BigDecimal amount;
  CreditStatus status;
  LocalDateTime creationDate;
}
