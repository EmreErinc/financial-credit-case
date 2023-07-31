package com.colendi.financial.credit.domain.repository;

import com.colendi.financial.credit.domain.CreditStatus;
import com.colendi.financial.credit.domain.entity.CreditEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface CustomCreditRepository {

  List<CreditEntity> findByUserId(long userId, int page, int size, Set<CreditStatus> statuses, Timestamp createdAt);
}
