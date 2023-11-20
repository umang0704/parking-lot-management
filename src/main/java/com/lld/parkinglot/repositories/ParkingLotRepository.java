package com.lld.parkinglot.repositories;

import com.lld.parkinglot.models.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}