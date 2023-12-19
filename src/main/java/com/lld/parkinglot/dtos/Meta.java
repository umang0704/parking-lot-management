package com.lld.parkinglot.dtos;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Meta implements Serializable {

  private String responseCode;
  private String responseMessage;
  private String requestId;
  private String responseId;

  public Meta(String responseCode, String responseMessage, String requestId, String responseId) {
    this.responseCode = responseCode;
    this.responseMessage = responseMessage;
    this.requestId = requestId;
    this.responseId = responseId;
  }
}
