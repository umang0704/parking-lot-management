package com.lld.parkinglot.design.factory;

import com.lld.parkinglot.design.strategy.SpotSelectionStrategy;
import com.lld.parkinglot.design.strategy.VehicleTypeSpotSelectionStrategy;
import com.lld.parkinglot.enums.SpotSelectionStrategyTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpotSelectionStrategyFactory {
  @Autowired private VehicleTypeSpotSelectionStrategy vehicleTypeSpotSelectionStrategy;

  public SpotSelectionStrategy getSpotSelectionStrategyByType(
      SpotSelectionStrategyTypes spotSelectionStrategyTypes) {
    switch (spotSelectionStrategyTypes) {
      case SINGLE:
        return vehicleTypeSpotSelectionStrategy;
      default:
        return null;
    }
  }
}
