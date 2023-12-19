package com.lld.parkinglot.repositories;

import com.lld.parkinglot.models.ParkingFloor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor, Long> {
  long countByParkingLot_Id(Long id);

  @Query(value = "SELECT p.id from ParkingFloor p where p.parkingLot.id = ?1")
  List<Long> findIdByParkingLot_Id(Long parkingLotId);;
}
