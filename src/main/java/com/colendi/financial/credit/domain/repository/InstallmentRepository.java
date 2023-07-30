package com.colendi.financial.credit.domain.repository;

import com.colendi.financial.credit.domain.InstallmentStatus;
import com.colendi.financial.credit.domain.entity.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<InstallmentEntity, Long> {

  List<InstallmentEntity> findByCreditId(long creditId);

  Long countByCreditIdAndStatus(long creditId, InstallmentStatus status);
}
