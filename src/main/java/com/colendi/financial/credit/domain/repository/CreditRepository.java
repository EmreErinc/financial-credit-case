package com.colendi.financial.credit.domain.repository;

import com.colendi.financial.credit.domain.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
