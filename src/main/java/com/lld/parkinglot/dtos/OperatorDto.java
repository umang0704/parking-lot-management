package com.lld.parkinglot.dtos;

import lombok.Data;

import java.io.Serializable;
@Data
public class OperatorDto implements Serializable {
    private String employeeId;
    private String employeeName;
}
