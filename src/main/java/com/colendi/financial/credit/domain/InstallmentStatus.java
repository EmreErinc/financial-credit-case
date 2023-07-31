package com.colendi.financial.credit.domain;

public enum InstallmentStatus {

  PENDING("Pending"),
  PARTIALLY_PAID("Partially Paid"),
  PAID("Paid"),
  OVERDUE("Overdue");

  private final String name;

  InstallmentStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
