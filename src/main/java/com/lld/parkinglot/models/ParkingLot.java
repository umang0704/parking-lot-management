package com.lld.parkinglot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_lot")
@Getter
@Setter
public class ParkingLot extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "parkingLot",fetch = FetchType.LAZY)
    private List<ParkingFloor> parkingFloors;

    @OneToMany(mappedBy = "parkingLot",fetch = FetchType.LAZY)
    private List<Gate> entryGate;

    @OneToMany(mappedBy = "parkingLot",fetch = FetchType.LAZY)
    private List<Gate> exitGate;


}
