package com.lld.parkinglot.services;

import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.Vehicle;
import com.lld.parkinglot.repositories.VehicleRepository;
import com.lld.parkinglot.services.encryption.AES256CryptoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VehicleService {
    private final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle fetchOrCreateVehicle(String vehicleNumber, VehicleType vehicleType) throws ApplicationException {
        Vehicle vehicle = vehicleRepository.findByVehicleNumberAndVehicleType(
                AES256CryptoConverter.encrypt(vehicleNumber),
                vehicleType
        );
        if(Objects.isNull(vehicle)){
            vehicle = new Vehicle();
            vehicle.setVehicleType(vehicleType);
            vehicle.setVehicleNumber(vehicleNumber);
            vehicleRepository.save(vehicle);
        }
        return vehicle;
    }
}
