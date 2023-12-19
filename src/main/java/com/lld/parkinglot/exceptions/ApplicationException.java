package com.lld.parkinglot.exceptions;

import com.lld.parkinglot.enums.ResponseCode;
import lombok.Data;

@Data
public class ApplicationException extends Exception {
  private Exception exception;
  private ResponseCode responseCode;
  private String customMessage;

  public ApplicationException(
      ResponseCode responseCode, Exception exception, String customMessage) {
    this.exception = exception;
    this.customMessage = customMessage;
    this.responseCode = responseCode;
  }

  public ApplicationException(ResponseCode responseCode, String customMessage) {
    this.customMessage = customMessage;
    this.responseCode = responseCode;
  }
}
