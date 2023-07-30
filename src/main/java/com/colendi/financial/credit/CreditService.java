package com.colendi.financial.credit;

import com.colendi.financial.commons.DoneResponse;
import com.colendi.financial.credit.api.model.request.LoanCreditRequest;
import com.colendi.financial.credit.api.model.request.PayInstallmentRequest;
import com.colendi.financial.credit.api.model.request.UpdateCreditStatusRequest;
import com.colendi.financial.credit.api.model.response.CreditDetailResponse;
import com.colendi.financial.credit.api.model.response.CreditListResponse;

public interface CreditService {

  DoneResponse loanCredit(LoanCreditRequest request);

  CreditDetailResponse getCreditDetail(long creditId);

  CreditListResponse getCreditList(long userId, int page, int size);

  DoneResponse payInstallment(PayInstallmentRequest request);

  DoneResponse updateCreditStatus(long creditId, UpdateCreditStatusRequest request);
}
