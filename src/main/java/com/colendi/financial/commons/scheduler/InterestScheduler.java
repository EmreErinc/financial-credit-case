package com.colendi.financial.commons.scheduler;

import com.colendi.financial.credit.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterestScheduler {

  private final CreditService creditService;

  @Scheduled(cron = "0 12 01 * * ?")
  public void detectLatePaymentInterests() {
    System.out.println("Periodic task worked at : " + new Date());
    creditService.calculateLatePaymentInterests();
  }
}
