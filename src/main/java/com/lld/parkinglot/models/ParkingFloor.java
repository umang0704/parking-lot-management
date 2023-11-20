package com.lld.parkinglot.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_floor")
@Data
public class ParkingFloor extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id",nullable = false)
    private ParkingLot parkingLot;

}
