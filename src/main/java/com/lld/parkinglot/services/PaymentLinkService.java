package com.lld.parkinglot.services;

import com.lld.parkinglot.models.Bill;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentLinkService {
  @Value(value = "pg.host")
  private String paymentHost;

  private final String delimiter = "#";

  public String createAndSetPaymentLink(Bill bill) {
    StringBuilder paymentStringBuilder = new StringBuilder();
    paymentStringBuilder.append(paymentHost);
    paymentStringBuilder.append(bill.getTicket().getId());
    paymentStringBuilder.append(delimiter);
    paymentStringBuilder.append(UUID.randomUUID());
    paymentStringBuilder.append(delimiter);
    paymentStringBuilder.append(new Date());
    return String.valueOf(paymentStringBuilder);
  }
}
