package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lld.parkinglot.enums.GateStatus;
import com.lld.parkinglot.enums.GateType;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gate")
@Data
public class Gate extends BaseModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "gate_status", nullable = false)
  private GateStatus gateStatus;

  @ManyToOne
  @JoinColumn(name = "parking_lot_id", nullable = false)
  @JsonIgnore
  private ParkingLot parkingLot;

  @Enumerated(EnumType.STRING)
  @Column(name = "gate_type", nullable = false)
  private GateType gateType;

  @Column(name = "gate_number", nullable = false)
  private Integer gateNumber;
}
