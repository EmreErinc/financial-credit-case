package com.colendi.financial.credit.domain;

public enum CreditStatus {

  REQUESTED("Requested"),
  PENDING("Pending"),
  APPROVED("Approved"),
  REJECTED("Rejected"),
  COMPLETED("Completed");

  private final String name;

  CreditStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
