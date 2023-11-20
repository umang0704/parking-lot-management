package com.lld.parkinglot.services;

import com.lld.parkinglot.design.factory.SpotSelectionStrategyFactory;
import com.lld.parkinglot.design.strategy.SpotSelectionStrategy;
import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.enums.SpotStatus;
import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.ParkingLot;
import com.lld.parkinglot.models.ParkingSpot;
import com.lld.parkinglot.repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public synchronized ParkingSpot getAndBlockParkingSpotByVehicleType(ParkingLot parkingLot, VehicleType vehicleType) throws ApplicationException {
        List<ParkingSpot> availableParkingSpot = parkingSpotRepository.findByParkingLotAndVehicleTypeAndSpotStatus(parkingLot.getId(), vehicleType, SpotStatus.EMPTY);
        if(CollectionUtils.isEmpty(availableParkingSpot)) throw new ApplicationException(ResponseCode.PL_200102,"Spot Not Found for type "+vehicleType);
        else{
            ParkingSpot parkingSpot = availableParkingSpot.get(0);
            try{
                updateSpotStatus(parkingSpot, SpotStatus.EMPTY, SpotStatus.BLOCKED);
            }catch (ApplicationException applicationException){
                availableParkingSpot.remove(parkingSpot);
                parkingSpot = availableParkingSpot.get(0);
                updateSpotStatus(parkingSpot, SpotStatus.EMPTY, SpotStatus.BLOCKED);
            }
            return parkingSpot;
        }
    }

    public synchronized void updateSpotStatus(ParkingSpot parkingSpot,SpotStatus oldStatus,SpotStatus newStatus) throws ApplicationException {
        int rowsAffected = parkingSpotRepository.updateParkingSpotStatus(newStatus, oldStatus, parkingSpot.getId());
        if(rowsAffected!=1) throw  new ApplicationException(ResponseCode.PL_500101,"Something Went Wrong while update Spot status");
    }


}
