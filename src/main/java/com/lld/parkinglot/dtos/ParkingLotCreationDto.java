package com.lld.parkinglot.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class ParkingLotCreationDto implements Serializable {
    @NonNull
    private Integer floors;
    @NonNull
    private Integer spotsOnFloor;
    @NonNull
    private Integer entryGates;
    @NonNull
    private Integer exitGates;
    @NonNull
    private Integer operatorCount;
}
