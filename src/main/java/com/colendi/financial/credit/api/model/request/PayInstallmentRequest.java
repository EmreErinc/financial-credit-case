package com.colendi.financial.credit.api.model.request;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PayInstallmentRequest {

  long installmentId;
  BigDecimal amount;
}
