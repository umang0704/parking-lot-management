package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_floor")
@Data
public class ParkingFloor extends BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  @JsonIgnore
  private Long id;

  @Column(name = "floor", nullable = false)
  private Integer floor;

  @ManyToOne
  @JoinColumn(name = "parking_lot_id", nullable = false)
  @JsonIgnore
  private ParkingLot parkingLot;
}
