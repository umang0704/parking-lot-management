package com.lld.parkinglot.design.strategy;

import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.ParkingLot;
import com.lld.parkinglot.models.ParkingSpot;
import org.springframework.stereotype.Component;

@Component
public interface SpotSelectionStrategy {
    ParkingSpot getParkingSpot(VehicleType vehicleType, ParkingLot parkingLot) throws ApplicationException;
}
