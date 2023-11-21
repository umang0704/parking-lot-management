package com.lld.parkinglot.design.strategy;

import com.lld.parkinglot.models.Bill;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BillAmountCreationStrategy {
    List<Integer> calculateBill(Bill bill);
}
