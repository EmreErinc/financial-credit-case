package com.colendi.financial.credit;

import com.colendi.financial.commons.model.DoneResponse;
import com.colendi.financial.credit.api.model.request.LoanCreditRequest;
import com.colendi.financial.credit.api.model.request.PayInstallmentRequest;
import com.colendi.financial.credit.api.model.request.UpdateCreditStatusRequest;
import com.colendi.financial.credit.api.model.response.CreditDetailResponse;
import com.colendi.financial.credit.api.model.response.CreditListResponse;
import com.colendi.financial.credit.domain.CreditStatus;

import java.time.LocalDate;

public interface CreditService {

  DoneResponse loanCredit(LoanCreditRequest request);

  CreditDetailResponse getCreditDetail(long creditId);

  CreditListResponse getCreditList(long userId, int page, int size, CreditStatus status, LocalDate createdAt);

  DoneResponse payInstallment(PayInstallmentRequest request);

  DoneResponse updateCreditStatus(long creditId, UpdateCreditStatusRequest request);

  void calculateLatePaymentInterests();
}
