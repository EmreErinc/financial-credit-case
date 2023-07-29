package com.colendi.financial.credit.api;

import com.colendi.financial.credit.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/credits")
@RequiredArgsConstructor
public class CreditController {

  private final CreditService creditService;
}
