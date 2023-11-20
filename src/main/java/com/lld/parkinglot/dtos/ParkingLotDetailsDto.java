package com.lld.parkinglot.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lld.parkinglot.models.Operator;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

@JsonFormat
@Data
public class ParkingLotDetailsDto implements Serializable {
    private Long parkingLotId;
    private Integer parkingFloors;
    private Integer parkingSpots;
    private List<Operator> operatorDtoList;
    private Integer entryGates;
    private Integer exitGates;

    public ParkingLotDetailsDto() {
    }

    public ParkingLotDetailsDto(Long parkingLotId, Integer parkingFloors, Integer parkingSpots, List<Operator> operatorDtoList, Integer entryGates, Integer exitGates) {
        this.parkingLotId = parkingLotId;
        this.parkingFloors = parkingFloors;
        this.parkingSpots = parkingSpots;
        this.operatorDtoList = operatorDtoList;
        this.entryGates = entryGates;
        this.exitGates = exitGates;
    }
}
