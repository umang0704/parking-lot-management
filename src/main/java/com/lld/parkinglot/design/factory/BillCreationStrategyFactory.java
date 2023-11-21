package com.lld.parkinglot.design.factory;

import com.lld.parkinglot.design.strategy.BillAmountCreationByTimeDurationStrategy;
import com.lld.parkinglot.design.strategy.BillAmountCreationStrategy;
import com.lld.parkinglot.enums.BillCreationStrategyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillCreationStrategyFactory {

    @Autowired
    private BillAmountCreationByTimeDurationStrategy billAmountCreationByTimeDurationStrategy;

    public BillAmountCreationStrategy getBillCreationFactory(BillCreationStrategyTypes billCreationStrategyTypes) {

        switch (billCreationStrategyTypes) {
            case DURATION:
                return billAmountCreationByTimeDurationStrategy;
            default:
                return null;
        }

    }
}
