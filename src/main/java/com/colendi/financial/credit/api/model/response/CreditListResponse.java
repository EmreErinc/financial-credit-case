package com.colendi.financial.credit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreditListResponse {

  List<CreditResponse> credits;
}
