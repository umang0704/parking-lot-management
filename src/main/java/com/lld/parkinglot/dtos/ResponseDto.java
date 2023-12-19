package com.lld.parkinglot.dtos;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResponseDto implements Serializable {
  private Object data;
  private Meta meta;
}
