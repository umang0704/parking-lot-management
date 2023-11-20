package com.lld.parkinglot.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Date;


@Data
@ResponseBody
public class TicketDto implements Serializable {
    @JsonProperty(value = "parking_lot_id")
    private Long parkingLotId;

    @JsonProperty(value = "operator")
    private String operatorId;

    @JsonProperty(value = "entry_time")
    private Date entryTime;

    @JsonProperty(value = "gate_number")
    private Integer gateNumber;

    @JsonProperty(value = "parking_floor")
    private Integer parkingFloor;

    @JsonProperty(value = "spot_number")
    private Integer spotNumber;
}
