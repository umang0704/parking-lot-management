package com.lld.parkinglot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

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

  @JsonProperty(value = "vehicle_number")
  private String vehicleNumber;
}
