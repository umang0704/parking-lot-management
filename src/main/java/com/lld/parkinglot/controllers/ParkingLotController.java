package com.lld.parkinglot.controllers;

import com.lld.parkinglot.constants.LoggingConstants;
import com.lld.parkinglot.dtos.Meta;
import com.lld.parkinglot.dtos.ParkingLotCreationDto;
import com.lld.parkinglot.dtos.ParkingLotDetailsDto;
import com.lld.parkinglot.dtos.ResponseDto;
import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.services.ParkingLotService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/parking-lot")
public class ParkingLotController {

  @Autowired private ParkingLotService parkingLotService;

  @PostMapping(path = "/create/metric")
  public ResponseDto createParkingLotOnMetric(
      @RequestBody @Validated ParkingLotCreationDto parkingLotCreationDto) {
    ParkingLotDetailsDto data = parkingLotService.createDto(parkingLotCreationDto);
    ResponseDto responseDto = new ResponseDto();
    responseDto.setData(data);
    responseDto.setMeta(
        new Meta(
            ResponseCode.PL_200101.getResponseCode(),
            ResponseCode.PL_200101.getMessage(),
            MDC.get(LoggingConstants.REQUEST_ID),
            MDC.get(LoggingConstants.RESPONSE_ID)));
    return responseDto;
  }
}
