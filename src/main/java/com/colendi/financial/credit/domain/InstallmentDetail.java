package com.colendi.financial.credit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class InstallmentDetail {

  long installmentId;
  LocalDate dueDate;
  BigDecimal amount;
  InstallmentStatus status;
  LocalDate paidDate;
  BigDecimal interestAmount;
  int interestDayCount;
  BigDecimal paidAmount;
}
