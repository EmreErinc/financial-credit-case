package com.colendi.financial.credit.domain.entity;

import com.colendi.financial.credit.domain.CreditStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(schema = "public", name = "credit")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class CreditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_id_seq")
  long id;

  @Column(name = "user_id")
  long userId;

  @Column(name = "status")
  CreditStatus status;

  @Column(name = "amount")
  BigDecimal amount;

  @Column(name = "installment_count")
  int installmentCount;

  @Builder.Default
  @Column(name = "created_at")
  Timestamp createdAt = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

  @Column(name = "updated_at")
  Timestamp updatedAt;
}