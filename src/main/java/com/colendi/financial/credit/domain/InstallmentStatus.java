package com.colendi.financial.credit.domain;

public enum InstallmentStatus {

  PENDING("Pending"),
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
