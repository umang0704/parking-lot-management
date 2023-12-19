package com.lld.parkinglot.dtos;

import java.io.Serializable;
import lombok.Data;

@Data
public class OperatorDto implements Serializable {
  private String employeeId;
  private String employeeName;
}
