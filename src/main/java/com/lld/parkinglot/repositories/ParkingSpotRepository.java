package com.lld.parkinglot.repositories;

import com.lld.parkinglot.enums.SpotStatus;
import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.models.ParkingSpot;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
  long countByParkingFloor_IdInAllIgnoreCase(Collection<Long> ids);

  @Query(
      "select p from ParkingSpot p "
          + "where p.parkingFloor.parkingLot.id = :id and p.supportedVehicleTypes = :supportedVehicleTypes and p.spotStatus = :spotStatus")
  List<ParkingSpot> findByParkingLotAndVehicleTypeAndSpotStatus(
      @Param("id") Long id,
      @Param("supportedVehicleTypes") VehicleType supportedVehicleTypes,
      @Param("spotStatus") SpotStatus spotStatus);

  @Query(
      "select p from ParkingSpot p "
          + "where p.spotStatus = ?1 and p.parkingFloor.parkingLot.id = ?2 and p.supportedVehicleTypes = ?3")
  List<ParkingSpot> findBySpotStatusAndParkingFloor_ParkingLot_IdAndSupportedVehicleTypes(
      SpotStatus spotStatus, Long id, VehicleType supportedVehicleTypes);

  @Transactional
  @Modifying
  @Query("update ParkingSpot p set p.spotStatus = ?1 where p.spotStatus = ?2 and p.id = ?3")
  int updateParkingSpotStatus(SpotStatus newStatus, SpotStatus oldStatus, Long id);
}
