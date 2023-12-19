package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lld.parkinglot.enums.SpotStatus;
import com.lld.parkinglot.enums.VehicleType;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_spot")
@Data
public class ParkingSpot extends BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  @JsonIgnore
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "spot_status", nullable = false)
  private SpotStatus spotStatus;

  @ManyToOne
  @JoinColumn(name = "parking_floor_id")
  private ParkingFloor parkingFloor;

  @Column(name = "vehicle_type")
  @Enumerated(EnumType.STRING)
  private VehicleType supportedVehicleTypes;

  @Column(name = "spot_number", nullable = false)
  private Integer spotNumber;
}
