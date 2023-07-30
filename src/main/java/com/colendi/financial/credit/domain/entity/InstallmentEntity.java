package com.colendi.financial.credit.domain.entity;

import com.colendi.financial.credit.domain.CreditStatus;
import com.colendi.financial.credit.domain.InstallmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(schema = "public", name = "installment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class InstallmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "installment_id_seq")
  long id;

  @Column(name = "credit_id")
  long creditId;

  @Column(name = "amount")
  BigDecimal amount;

  @Column(name = "status")
  InstallmentStatus status;

  @Column(name = "due_date")
  Timestamp dueDate;

  @Column(name = "paid_date")
  Timestamp paidDate;

  @Builder.Default
  @Column(name = "created_at")
  Timestamp createdAt = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

  @Column(name = "updated_at")
  Timestamp updatedAt;
}