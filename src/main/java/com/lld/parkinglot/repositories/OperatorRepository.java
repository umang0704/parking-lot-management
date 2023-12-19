package com.lld.parkinglot.repositories;

import com.lld.parkinglot.models.Operator;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
  Optional<Operator> findByIdAndParkingLot_Id(Long id, Long parkingLotId);
}
