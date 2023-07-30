package com.colendi.financial.credit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PayInstallmentRequest {

  long installmentId;
  BigDecimal amount;
}
