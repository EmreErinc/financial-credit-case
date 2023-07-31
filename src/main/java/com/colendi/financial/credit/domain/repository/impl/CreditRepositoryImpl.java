package com.colendi.financial.credit.domain.repository.impl;

import com.colendi.financial.credit.domain.CreditStatus;
import com.colendi.financial.credit.domain.entity.CreditEntity;
import com.colendi.financial.credit.domain.repository.CustomCreditRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CreditRepositoryImpl implements CustomCreditRepository {

  private final EntityManager entityManager;

  @Override
  public List<CreditEntity> findByUserId(long userId, int page, int size, Set<CreditStatus> statuses, Timestamp createdAt) {
    return entityManager.createQuery("SELECT c FROM CreditEntity c WHERE c.userId = :userId AND c.status in :statuses AND c.createdAt > :createdAt ORDER BY c.createdAt DESC LIMIT :limit_value OFFSET :offset_value", CreditEntity.class)
        .setParameter("userId", userId)
        .setParameter("statuses", statuses)
        .setParameter("limit_value", size)
        .setParameter("offset_value", (page - 1) * size)
        .setParameter("createdAt", createdAt)
        .getResultList();
  }
}
