package com.lld.parkinglot.controllers;

import com.lld.parkinglot.dtos.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/bill")
public class BillController {
    public ResponseDto createBill(Long ticket){
        return null;
    }
}
