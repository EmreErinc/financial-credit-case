package com.colendi.financial.credit.domain;

import com.colendi.financial.credit.api.model.response.CreditResponse;
import com.colendi.financial.credit.domain.entity.CreditEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CreditMapper {

  public Function<CreditEntity, CreditResponse> mapToCreditResponse() {
    return creditEntity ->
        CreditResponse.builder()
            .creditId(creditEntity.getId())
            .amount(creditEntity.getAmount())
            .status(creditEntity.getStatus())
            .creationDate(creditEntity.getCreatedAt().toLocalDateTime())
            .build();
  }
}
