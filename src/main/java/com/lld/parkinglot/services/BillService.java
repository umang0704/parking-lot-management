package com.lld.parkinglot.services;

import com.lld.parkinglot.design.factory.BillCreationStrategyFactory;
import com.lld.parkinglot.design.strategy.BillAmountCreationStrategy;
import com.lld.parkinglot.dtos.BillDto;
import com.lld.parkinglot.enums.BillCreationStrategyTypes;
import com.lld.parkinglot.enums.SpotStatus;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.*;
import com.lld.parkinglot.repositories.BillRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

  @Autowired private BillRepository billRepository;

  @Autowired private VehicleService vehicleService;

  @Autowired private OperatorService operatorService;

  @Autowired private GateService gateService;

  @Autowired private ParkingLotService parkingLotService;

  @Autowired private TicketService ticketService;

  @Autowired private PaymentLinkService paymentLinkService;

  @Autowired private BillCreationStrategyFactory billCreationStrategyFactory;

  @Autowired private ParkingSpotService parkingSpotService;

  // constants
  private static final Integer PART_WHOLE_INDEX = 0;
  private static final Integer PART_DECIMAL_INDEX = 1;

  public BillDto produceBill(Long parkingLotId, String vehicleNumber, Long operatorId, Long gateId)
      throws ApplicationException {
    Date exitTime = new Date();
    ParkingLot parkingLot = parkingLotService.checkIfParkingLotExist(parkingLotId);
    // validate input
    Gate exitGate = gateService.checkIfOpenGateExist(gateId, parkingLot);
    Vehicle vehicle = vehicleService.fetchVehicleDetails(vehicleNumber);
    Operator operator = operatorService.checkIfOperatorExist(operatorId, parkingLot);
    Ticket ticket = ticketService.fetchTicket(vehicle);

    // create bill
    Bill bill = createBillAndSetBasicDetails(exitTime, exitGate, operator, ticket);
    calculateAndSetBill(bill);
    String paymentLink = paymentLinkService.createAndSetPaymentLink(bill);
    bill.setPaymentLink(paymentLink);
    billRepository.save(bill);

    // create Dto
    BillDto billDto = createBillDto(ticket, bill);
    updateParkingSpotStatusAfterBillCreation(ticket);
    return billDto;
  }

  private void updateParkingSpotStatusAfterBillCreation(Ticket ticket) throws ApplicationException {
    ParkingSpot parkingSpot = ticket.getParkingSpot();
    parkingSpot.setSpotStatus(SpotStatus.EMPTY);
    parkingSpotService.updateSpotStatus(parkingSpot, SpotStatus.EMPTY, SpotStatus.FILLED);
  }

  private static Bill createBillAndSetBasicDetails(
      Date exitTime, Gate exitGate, Operator operator, Ticket ticket) {
    Bill bill = new Bill();
    bill.setGate(exitGate);
    bill.setOperator(operator);
    bill.setTicket(ticket);
    bill.setExitTime(exitTime);
    return bill;
  }

  private void calculateAndSetBill(Bill bill) {
    BillAmountCreationStrategy billCreationFactory =
        billCreationStrategyFactory.getBillCreationFactory(BillCreationStrategyTypes.DURATION);
    List<Integer> amount = billCreationFactory.calculateBill(bill);
    bill.setAmountPartWhole(amount.get(PART_WHOLE_INDEX));
    bill.setAmountPartDecimal(amount.get(PART_DECIMAL_INDEX));
  }

  private static BillDto createBillDto(Ticket ticket, Bill bill) {
    BillDto billDto = new BillDto();
    billDto.setPaymentLink(bill.getPaymentLink());
    billDto.setExitTime(bill.getExitTime());
    billDto.setGateNumber(bill.getGate().getGateNumber());
    billDto.setOperatorName(bill.getOperator().getName());
    billDto.setVehicle_number(bill.getTicket().getVehicle().getVehicleNumber());
    billDto.setAmount(
        String.valueOf(
            new StringBuilder()
                .append(bill.getAmountPartWhole())
                .append(".")
                .append(bill.getAmountPartDecimal())));
    billDto.setTicket(ticket);
    return billDto;
  }
}
