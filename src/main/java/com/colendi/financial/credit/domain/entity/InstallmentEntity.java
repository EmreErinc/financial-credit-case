package com.colendi.financial.credit.domain.entity;

import com.colendi.financial.credit.domain.CreditStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "public", name = "installment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class InstallmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "installment_id_seq")
  long id;

  @Column(name = "credit_id")
  long creditId;

  @Column(name = "amount")
  BigDecimal amount;

  @Column(name = "created_at")
  Timestamp createdAt;

  @Column(name = "updated_at")
  Timestamp updatedAt;
}