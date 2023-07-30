package com.colendi.financial.credit.domain.repository;

import com.colendi.financial.credit.domain.entity.CreditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CreditRepository extends CrudRepository<CreditEntity, Long> {

  Optional<CreditEntity> findById(long creditId);

  Page<CreditEntity> findByUserId(long userId, Pageable pageable);
}
