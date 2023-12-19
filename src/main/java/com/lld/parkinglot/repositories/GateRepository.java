package com.lld.parkinglot.repositories;

import com.lld.parkinglot.enums.GateStatus;
import com.lld.parkinglot.models.Gate;
import com.lld.parkinglot.models.ParkingLot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GateRepository extends JpaRepository<Gate, Long> {
  Optional<Gate> findByIdAndParkingLot_Id(Long id, Long parkingLotId);

  @Query(
      nativeQuery = true,
      value = "SELECT count(*) from gate g where g.parking_lot_id = :id and g.gate_type = :type")
  long getGatesByTypeAndParkingLotId(@Param("id") Long parkingLotId, @Param("type") String type);

  @Transactional
  @Modifying
  @Query("update Gate g set g.gateStatus = ?1 where g.parkingLot = ?2 and g.gateStatus = ?3")
  int updateGateStatusByParkingLotAndGateStatus(
      GateStatus oldGateStatus, ParkingLot parkingLot, GateStatus currentGateStatus);
}
