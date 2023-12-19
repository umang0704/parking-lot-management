package com.lld.parkinglot.dtos;

import java.io.Serializable;
import lombok.Data;
import lombok.NonNull;

@Data
public class ParkingLotCreationDto implements Serializable {
  @NonNull private Integer floors;
  @NonNull private Integer spotsOnFloor;
  @NonNull private Integer entryGates;
  @NonNull private Integer exitGates;
  @NonNull private Integer operatorCount;
}
