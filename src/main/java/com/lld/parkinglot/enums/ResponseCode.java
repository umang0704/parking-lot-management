package com.lld.parkinglot.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
  // 200
  PL_200101("PL_200101", "SUCCESS", HttpStatus.OK),
  PL_200102("PL_200102", "Spot Not Found", HttpStatus.OK),
  // 400
  PL_400401("PL_400401", "Parking Lot Not Found", HttpStatus.BAD_REQUEST),
  PL_400402("PL_400402", "Operator Not Found", HttpStatus.BAD_REQUEST),
  PL_400403("PL_400402", "Gate Not Found", HttpStatus.BAD_REQUEST),
  PL_400404(
      "PL_400404", "Ticket already exist for vehicle with Open state.", HttpStatus.BAD_REQUEST),
  PL_400405("PL_400404", "Vehicle Not Found for vehicle Number", HttpStatus.BAD_REQUEST),

  // 500
  PL_500101("PL_500101", "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
  PL_500102(
      "PL_500102",
      "INTERNAL_SERVER_ERROR : ERROR IN ENCRYPTION/DECRYPTION",
      HttpStatus.INTERNAL_SERVER_ERROR),
  ;
  private String responseCode;
  private String message;
  private HttpStatus httpStatus;

  ResponseCode(String responseCode, String message, HttpStatus httpStatus) {
    this.responseCode = responseCode;
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
