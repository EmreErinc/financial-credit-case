package com.colendi.financial.credit.domain.repository;

import com.colendi.financial.credit.domain.entity.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentRepository extends JpaRepository<InstallmentEntity, Long> {
}
