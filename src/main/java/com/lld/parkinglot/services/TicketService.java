package com.lld.parkinglot.services;

import com.lld.parkinglot.design.factory.SpotSelectionStrategyFactory;
import com.lld.parkinglot.design.strategy.SpotSelectionStrategy;
import com.lld.parkinglot.dtos.TicketDto;
import com.lld.parkinglot.enums.*;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.*;
import com.lld.parkinglot.repositories.TicketRepository;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
  private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
  @Autowired private ParkingLotService parkingLotService;
  @Autowired private GateService gateService;
  @Autowired private OperatorService operatorService;
  @Autowired private VehicleService vehicleService;

  @Autowired private ParkingSpotService parkingSpotService;
  @Autowired private TicketRepository ticketRepository;

  @Autowired private SpotSelectionStrategyFactory spotSelectionStrategyFactory;

  @Transactional
  public synchronized TicketDto createTicket(
      Long parkingLotId,
      Long operatorId,
      Long gateId,
      String vehicleNumber,
      VehicleType vehicleType)
      throws ApplicationException {
    // validate input
    ParkingLot parkingLot = parkingLotService.checkIfParkingLotExist(parkingLotId);
    Gate gate = gateService.checkIfOpenGateExist(gateId, parkingLot);
    Operator operator = operatorService.checkIfOperatorExist(operatorId, parkingLot);

    // create vehicle if not exists
    Vehicle vehicle = vehicleService.fetchOrCreateVehicle(vehicleNumber, vehicleType);
    Ticket existingTicket =
        ticketRepository.findByVehicleAndTicketStatus(vehicle, TicketStatus.OPEN);
    if (Objects.nonNull(existingTicket))
      throw new ApplicationException(
          ResponseCode.PL_400404, "Ticket Already Exist in Open State : " + existingTicket);

    SpotSelectionStrategy spotSelectionStrategyByType =
        spotSelectionStrategyFactory.getSpotSelectionStrategyByType(
            SpotSelectionStrategyTypes.SINGLE);
    ParkingSpot parkingSpot = spotSelectionStrategyByType.getParkingSpot(vehicleType, parkingLot);
    Ticket ticket;
    // create ticket
    try {
      ticket = new Ticket();
      ticket.setEntryTime(new Date());
      ticket.setParkingSpot(parkingSpot);
      ticket.setVehicle(vehicle);
      ticket.setGate(gate);
      ticket.setOperator(operator);
      ticket.setSpotNumber(parkingSpot.getSpotNumber());
      ticket.setTicketStatus(TicketStatus.OPEN);

      ticketRepository.save(ticket);
      parkingSpotService.updateSpotStatus(parkingSpot, SpotStatus.BLOCKED, SpotStatus.FILLED);
    } catch (ApplicationException applicationException) {
      throw applicationException;
    } catch (Exception exception) {
      throw new ApplicationException(ResponseCode.PL_500101, "Something Went Wrong,");
    }

    return createTicketDto(gate, operator, parkingSpot, ticket);
  }

  public TicketDto createTicketDto(
      Gate gate, Operator operator, ParkingSpot parkingSpot, Ticket ticket) {
    TicketDto ticketDto = new TicketDto();
    ticketDto.setEntryTime(ticket.getEntryTime());
    ticketDto.setParkingFloor(parkingSpot.getParkingFloor().getFloor());
    ticketDto.setGateNumber(gate.getGateNumber());
    ticketDto.setOperatorId(operator.getEmployeeId().toString());
    ticketDto.setSpotNumber(ticket.getSpotNumber());
    ticketDto.setParkingLotId(gate.getParkingLot().getId());
    ticketDto.setVehicleNumber(ticket.getVehicle().getVehicleNumber());
    return ticketDto;
  }

  public Ticket fetchTicket(Vehicle vehicle) throws ApplicationException {
    List<Ticket> ticket = ticketRepository.getByTicketStatusAndVehicle(TicketStatus.OPEN, vehicle);
    if (ticket.size() != 1) {
      if (ticket.size() == 0)
        logger.error("No Open Ticket Found for Vehicle: " + vehicle.getVehicleNumber());
      else if (ticket.size() > 0)
        logger.error("More than one Open Ticket found for Vehicle: " + vehicle.getVehicleNumber());
      throw new ApplicationException(ResponseCode.PL_400405, "Valid Ticket Not Found For Vehicle.");
    }
    return ticket.get(0);
  }
}
