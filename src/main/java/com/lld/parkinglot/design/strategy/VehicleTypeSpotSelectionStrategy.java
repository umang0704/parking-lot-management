package com.lld.parkinglot.design.strategy;

import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.ParkingLot;
import com.lld.parkinglot.models.ParkingSpot;
import com.lld.parkinglot.services.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleTypeSpotSelectionStrategy implements SpotSelectionStrategy {
  @Autowired private ParkingSpotService parkingSpotService;

  @Override
  public ParkingSpot getParkingSpot(VehicleType vehicleType, ParkingLot parkingLot)
      throws ApplicationException {
    return parkingSpotService.getAndBlockParkingSpotByVehicleType(parkingLot, vehicleType);
  }
}
