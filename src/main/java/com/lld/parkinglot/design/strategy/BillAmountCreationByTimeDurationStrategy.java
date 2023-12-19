package com.lld.parkinglot.design.strategy;

import com.lld.parkinglot.models.Bill;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class BillAmountCreationByTimeDurationStrategy implements BillAmountCreationStrategy {
  private Map<Integer, List<Integer>> prices;

  {
    prices = new HashMap<>();
    prices.put(2, Arrays.asList(15, 10));
    prices.put(4, Arrays.asList(30, 20));
    prices.put(6, Arrays.asList(60, 40));
    prices.put(-1, Arrays.asList(120, 0));
  }

  @Override
  public List<Integer> calculateBill(Bill bill) {
    List<Integer> billAmount = new ArrayList<>();
    Date entryTime = bill.getTicket().getEntryTime();
    Date exitTime = bill.getExitTime();
    long parkingHours = (exitTime.getTime() - entryTime.getTime()) / (1000 * 60 * 60);

    if (parkingHours <= 2) {
      billAmount.addAll(prices.get(2));
    } else if (parkingHours <= 4) {
      billAmount.addAll(prices.get(4));
    } else if (parkingHours <= 6) {
      billAmount.addAll(prices.get(6));
    } else {
      billAmount.addAll(prices.get(-1));
    }

    return billAmount;
  }
}
