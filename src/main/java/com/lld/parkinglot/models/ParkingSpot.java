package com.lld.parkinglot.models;

import com.lld.parkinglot.enums.SpotStatus;
import com.lld.parkinglot.enums.VehicleType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_spot")
@Data
public class ParkingSpot extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "spot_status",nullable = false)
    private SpotStatus spotStatus;

    @ManyToOne
    @JoinColumn(name = "parking_floor_id")
    private ParkingFloor parkingFloor;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType supportedVehicleTypes;

    @Column(name = "spot_number",nullable = false)
    private Integer spotNumber;

}