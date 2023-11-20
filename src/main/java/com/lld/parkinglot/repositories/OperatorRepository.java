package com.lld.parkinglot.repositories;

import com.lld.parkinglot.models.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    Optional<Operator> findByIdAndParkingLot_Id(Long id, Long parkingLotId);

}