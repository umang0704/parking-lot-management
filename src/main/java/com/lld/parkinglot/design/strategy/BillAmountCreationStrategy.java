package com.lld.parkinglot.design.strategy;

import com.lld.parkinglot.models.Bill;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface BillAmountCreationStrategy {
  List<Integer> calculateBill(Bill bill);
}
