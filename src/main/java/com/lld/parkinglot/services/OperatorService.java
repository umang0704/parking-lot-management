package com.lld.parkinglot.services;

import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.Operator;
import com.lld.parkinglot.models.ParkingLot;
import com.lld.parkinglot.repositories.OperatorRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {
  @Autowired private OperatorRepository operatorRepository;

  public Operator checkIfOperatorExist(Long operatorId, ParkingLot parkingLot)
      throws ApplicationException {
    Optional<Operator> operator =
        operatorRepository.findByIdAndParkingLot_Id(operatorId, parkingLot.getId());
    if (operator.isPresent()) return operator.get();
    else
      throw new ApplicationException(
          ResponseCode.PL_400402, "Operator with id: " + operatorId + "not found.");
  }
}
