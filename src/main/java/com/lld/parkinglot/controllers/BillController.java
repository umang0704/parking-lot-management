package com.lld.parkinglot.controllers;

import com.lld.parkinglot.constants.LoggingConstants;
import com.lld.parkinglot.dtos.BillDto;
import com.lld.parkinglot.dtos.Meta;
import com.lld.parkinglot.dtos.ResponseDto;
import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.services.BillService;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/bill")
public class BillController {
  @Autowired private BillService billService;

  @PostMapping(path = "/create")
  public ResponseDto createBill(
      @RequestParam(name = "gateId") @NonNull Long parkingLotId,
      @RequestParam(name = "vehicleNumber") @NotBlank String vehicleNumber,
      @RequestParam(name = "operatorId") @NonNull Long operatorId,
      @RequestParam(name = "gateId") @NonNull Long gateId)
      throws ApplicationException {
    BillDto billDto = billService.produceBill(parkingLotId, vehicleNumber, operatorId, gateId);
    ResponseDto responseDto = new ResponseDto();
    responseDto.setData(billDto);
    responseDto.setMeta(
        new Meta(
            ResponseCode.PL_200101.getResponseCode(),
            ResponseCode.PL_200101.getMessage(),
            Strings.isBlank(MDC.get(LoggingConstants.REQUEST_ID))
                ? UUID.randomUUID().toString()
                : MDC.get(LoggingConstants.REQUEST_ID),
            Strings.isBlank(MDC.get(LoggingConstants.RESPONSE_ID))
                ? UUID.randomUUID().toString()
                : MDC.get(LoggingConstants.RESPONSE_ID)));
    return responseDto;
  }
}
