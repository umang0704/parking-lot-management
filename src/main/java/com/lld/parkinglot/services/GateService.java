package com.lld.parkinglot.services;

import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.enums.GateStatus;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.Gate;
import com.lld.parkinglot.models.Operator;
import com.lld.parkinglot.models.ParkingLot;
import com.lld.parkinglot.repositories.GateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GateService {
    private final Logger logger = LoggerFactory.getLogger(GateService.class);
    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private OperatorService operatorService;


    public int openOrCloseGate(Long parkingLotId, Long operatorId,GateStatus gateStatus) throws ApplicationException {
        //validate input
        ParkingLot parkingLot = parkingLotService.checkIfParkingLotExist(parkingLotId);
        Operator operator = operatorService.checkIfOperatorExist(operatorId,parkingLot);
        if(GateStatus.OPEN.equals(gateStatus)){
            int openedCount = gateRepository.updateGateStatusByParkingLotAndGateStatus(GateStatus.OPEN, parkingLot, GateStatus.CLOSED);
            if(openedCount <= 0){
                logger.error("Either Gates were already Open or No Gates Exist for parking lot.");
            }
            return openedCount;
        }else {
            int closedCount = gateRepository.updateGateStatusByParkingLotAndGateStatus(GateStatus.CLOSED, parkingLot, GateStatus.OPEN);
            if(closedCount <= 0){
                logger.error("Either Gates were already Closed or No Gates Exist for parking lot.");
            }
            return closedCount;
        }
    }

    public Gate checkIfOpenGateExist(Long gateId,ParkingLot parkingLot) throws ApplicationException {
        Optional<Gate> gate = gateRepository.findByIdAndParkingLot_Id(gateId, parkingLot.getId());
        if(gate.isPresent() && gate.get().getGateStatus().equals(GateStatus.OPEN)) return gate.get();
        else if(gate.isPresent() && gate.get().getGateStatus().equals(GateStatus.CLOSED)) throw new ApplicationException(ResponseCode.PL_400402,"Gate with id: "+gate.get().getGateNumber()+" is closed.");
        else throw new ApplicationException(ResponseCode.PL_400402,"Gate with id: "+gate.get().getId()+"not found.");
    }

}
