package com.colendi.financial.credit.impl;

import com.colendi.financial.credit.CreditService;
import com.colendi.financial.credit.domain.repository.CreditRepository;
import com.colendi.financial.credit.domain.repository.InstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

  private final CreditRepository creditRepository;
  private final InstallmentRepository installmentRepository;
}
