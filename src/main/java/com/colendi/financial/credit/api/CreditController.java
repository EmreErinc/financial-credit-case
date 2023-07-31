package com.colendi.financial.credit.api;

import com.colendi.financial.commons.model.DoneResponse;
import com.colendi.financial.credit.CreditService;
import com.colendi.financial.credit.api.model.request.LoanCreditRequest;
import com.colendi.financial.credit.api.model.request.PayInstallmentRequest;
import com.colendi.financial.credit.api.model.request.UpdateCreditStatusRequest;
import com.colendi.financial.credit.api.model.response.CreditDetailResponse;
import com.colendi.financial.credit.api.model.response.CreditListResponse;
import com.colendi.financial.credit.domain.CreditStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/credits")
@RequiredArgsConstructor
public class CreditController {

  private final CreditService creditService;

  @PostMapping("/loan")
  public ResponseEntity<DoneResponse> loanCredit(@RequestBody LoanCreditRequest request) {
    return ResponseEntity.ok(creditService.loanCredit(request));
  }

  @GetMapping("/by/{userId}")
  public ResponseEntity<CreditListResponse> getCreditList(@PathVariable long userId,
                                                          @RequestParam(required = false, defaultValue = "1") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size,
                                                          @RequestParam(required = false) CreditStatus status,
                                                          @RequestParam(required = false) LocalDate createdAt) {
    return ResponseEntity.ok(creditService.getCreditList(userId, page, size, status, createdAt));
  }

  @GetMapping("/{creditId}")
  public ResponseEntity<CreditDetailResponse> getCreditDetail(@PathVariable long creditId) {
    return ResponseEntity.ok(creditService.getCreditDetail(creditId));
  }

  @PostMapping("/pay")
  public ResponseEntity<DoneResponse> payInstallment(@RequestBody PayInstallmentRequest request) {
    return ResponseEntity.ok(creditService.payInstallment(request));
  }

  @PutMapping("/{creditId}/status")
  public ResponseEntity<DoneResponse> updateCreditStatus(@PathVariable long creditId,
                                                         @RequestBody UpdateCreditStatusRequest request) {
    return ResponseEntity.ok(creditService.updateCreditStatus(creditId, request));
  }
}
