package com.colendi.financial.credit.api.model.request;

import com.colendi.financial.credit.domain.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UpdateCreditStatusRequest {

  CreditStatus updatedStatus;
}
