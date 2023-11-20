package com.lld.parkinglot.dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Data
public class ResponseDto implements Serializable {
    private Object data;
    private Meta meta;
}
