package com.lld.parkinglot.repositories;

import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("select v from Vehicle v where v.vehicleNumber = ?1 and v.vehicleType = ?2")
    Vehicle findByVehicleNumberAndVehicleType(String vehicleNumber, VehicleType vehicleType);


    @Query("select v from Vehicle v where v.vehicleNumber = ?1")
    Vehicle findByVehicleNumber(String vehicleNumber);
}