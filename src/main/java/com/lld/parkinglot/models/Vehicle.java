package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lld.parkinglot.enums.VehicleType;
import com.lld.parkinglot.services.encryption.AES256CryptoConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
@Data
public class Vehicle extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @PostLoad
    public void postLoad() {
        try {
            this.vehicleNumber = AES256CryptoConverter.decrypt(vehicleNumber);
            System.out.println(this.vehicleNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PrePersist
    public void prePersist() {
        try {
            this.vehicleNumber = AES256CryptoConverter.encrypt(vehicleNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
