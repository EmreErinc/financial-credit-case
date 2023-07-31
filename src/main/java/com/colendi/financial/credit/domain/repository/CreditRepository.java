package com.colendi.financial.credit.domain.repository;

import com.colendi.financial.credit.domain.entity.CreditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreditRepository extends CrudRepository<CreditEntity, Long>, CustomCreditRepository {

  Optional<CreditEntity> findById(long creditId);

  Page<CreditEntity> findByUserId(long userId, Pageable pageable);
}
