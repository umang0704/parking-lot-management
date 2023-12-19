package com.lld.parkinglot.controllers;

import com.lld.parkinglot.constants.LoggingConstants;
import com.lld.parkinglot.dtos.Meta;
import com.lld.parkinglot.dtos.ResponseDto;
import com.lld.parkinglot.enums.GateStatus;
import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.services.GateService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/gate")
public class GateController {
  @Autowired private GateService gateService;

  @PutMapping(path = "/open")
  public ResponseDto openGates(
      @RequestParam(name = "parkingLotId") @NonNull Long parkingLotId,
      @RequestParam(name = "operatorId") @NonNull Long operatorId)
      throws ApplicationException {
    ResponseDto responseDto = new ResponseDto();
    int openedCount = gateService.openOrCloseGate(parkingLotId, operatorId, GateStatus.OPEN);
    responseDto.setData(openedCount);
    responseDto.setMeta(
        new Meta(
            ResponseCode.PL_200101.getResponseCode(),
            ResponseCode.PL_200101.getMessage(),
            MDC.get(LoggingConstants.REQUEST_ID),
            MDC.get(LoggingConstants.RESPONSE_ID)));
    return responseDto;
  }

  @PutMapping(path = "/close")
  public ResponseDto closeGates(
      @RequestParam(name = "parkingLotId") @NonNull Long parkingLotId,
      @RequestParam(name = "operatorId") @NonNull Long operatorId)
      throws ApplicationException {
    ResponseDto responseDto = new ResponseDto();
    int closedCount = gateService.openOrCloseGate(parkingLotId, operatorId, GateStatus.CLOSED);
    responseDto.setData(closedCount);
    responseDto.setMeta(
        new Meta(
            ResponseCode.PL_200101.getResponseCode(),
            ResponseCode.PL_200101.getMessage(),
            MDC.get(LoggingConstants.REQUEST_ID),
            MDC.get(LoggingConstants.RESPONSE_ID)));
    return responseDto;
  }
}
