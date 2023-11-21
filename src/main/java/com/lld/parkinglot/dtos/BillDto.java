package com.lld.parkinglot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lld.parkinglot.models.Ticket;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class BillDto implements Serializable {
    @JsonProperty("operator_name")
    private String operatorName;
    @JsonProperty("vehicle_number")
    private String vehicle_number;

    @JsonProperty("payment_link")
    private String paymentLink;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("gate_number")
    private Integer gateNumber;

    @JsonProperty("ticket")
    private Ticket ticket;

    @JsonProperty("exit_time")
    private Date exitTime;

}
