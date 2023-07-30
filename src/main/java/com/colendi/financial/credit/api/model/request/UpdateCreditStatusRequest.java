package com.colendi.financial.credit.api.model.request;

import com.colendi.financial.credit.domain.CreditStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateCreditStatusRequest {

  CreditStatus updatedStatus;
}
