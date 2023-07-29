package com.colendi.financial.credit.domain;

public enum CreditStatus {

  PENDING("Pending"),
  APPROVED("Approved"),
  REJECTED("Rejected");

  private final String name;

  CreditStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
