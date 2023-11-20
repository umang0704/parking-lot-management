package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "operator")
@Data
public class Operator extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    @JsonIgnore
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "employee_id",nullable = false)
    private UUID employeeId;

    @ManyToOne
    @JoinColumn(name = "operator_parking_lot_id",nullable = false)
    @JsonIgnore
    private ParkingLot parkingLot;
}
