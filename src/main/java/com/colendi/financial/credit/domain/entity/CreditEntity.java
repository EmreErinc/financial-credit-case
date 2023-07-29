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
@Table(schema = "public", name = "credit")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

  @Column(name = "created_at")
  Timestamp createdAt;

  @Column(name = "updated_at")
  Timestamp updatedAt;
}