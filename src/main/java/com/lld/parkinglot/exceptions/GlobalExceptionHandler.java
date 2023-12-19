package com.lld.parkinglot.exceptions;

import com.lld.parkinglot.constants.LoggingConstants;
import com.lld.parkinglot.dtos.Meta;
import com.lld.parkinglot.dtos.ResponseDto;
import com.lld.parkinglot.enums.ResponseCode;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<ResponseDto> handleApplicationException(
      ApplicationException applicationException) {
    logger.error("ERROR_MESSAGE : {}", applicationException.getCustomMessage());
    logger.error("ERROR_ENCOUNTERED : {}", applicationException.getException());
    Meta meta =
        new Meta(
            applicationException.getResponseCode().getResponseCode().toString(),
            applicationException.getCustomMessage(),
            Strings.isBlank(MDC.get(LoggingConstants.REQUEST_ID))
                ? UUID.randomUUID().toString()
                : MDC.get(LoggingConstants.REQUEST_ID),
            Strings.isBlank(MDC.get(LoggingConstants.RESPONSE_ID))
                ? UUID.randomUUID().toString()
                : MDC.get(LoggingConstants.RESPONSE_ID));
    ResponseDto responseDto = new ResponseDto();
    responseDto.setData(null);
    responseDto.setMeta(meta);
    return new ResponseEntity<>(
        responseDto, applicationException.getResponseCode().getHttpStatus());
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseDto handleRuntimeException(RuntimeException applicationException) {
    logger.error("ERROR_ENCOUNTERED : {}", applicationException);
    Meta meta =
        new Meta(
            ResponseCode.PL_500101.getResponseCode(),
            ResponseCode.PL_500101.getMessage(),
            Strings.isBlank(MDC.get(LoggingConstants.REQUEST_ID))
                ? UUID.randomUUID().toString()
                : MDC.get(LoggingConstants.REQUEST_ID),
            Strings.isBlank(MDC.get(LoggingConstants.RESPONSE_ID))
                ? UUID.randomUUID().toString()
                : MDC.get(LoggingConstants.RESPONSE_ID));
    ResponseDto responseDto = new ResponseDto();
    responseDto.setData(null);
    responseDto.setMeta(meta);
    return responseDto;
  }
}
