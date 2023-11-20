package com.lld.parkinglot.services;

import com.lld.parkinglot.design.factory.SpotSelectionStrategyFactory;
import com.lld.parkinglot.design.strategy.SpotSelectionStrategy;
import com.lld.parkinglot.dtos.TicketDto;
import com.lld.parkinglot.enums.*;
import com.lld.parkinglot.exceptions.ApplicationException;
import com.lld.parkinglot.models.*;
import com.lld.parkinglot.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Service
public class TicketService {
    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private GateService gateService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ParkingSpotService parkingSpotService;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SpotSelectionStrategyFactory spotSelectionStrategyFactory;

    @Transactional
    public synchronized TicketDto createTicket(Long parkingLotId,
                                  Long operatorId,
                                  Long gateId,
                                  String vehicleNumber,
                                  VehicleType vehicleType) throws ApplicationException {
        //validate input
        ParkingLot parkingLot = parkingLotService.checkIfParkingLotExist(parkingLotId);
        Gate gate = gateService.checkIfOpenGateExist(gateId,parkingLot);
        Operator operator = operatorService.checkIfOperatorExist(operatorId,parkingLot);

        //create vehicle if not exists
        Vehicle vehicle = vehicleService.fetchOrCreateVehicle(vehicleNumber, vehicleType);
        Ticket existingTicket = ticketRepository.findByVehicleAndTicketStatus(vehicle, TicketStatus.OPEN);
        if(Objects.nonNull(existingTicket))
            throw new ApplicationException(ResponseCode.PL_400404,"Ticket Already Exist in Open State : "+ existingTicket);


        SpotSelectionStrategy spotSelectionStrategyByType = spotSelectionStrategyFactory.getSpotSelectionStrategyByType(SpotSelectionStrategyTypes.SINGLE);
        ParkingSpot parkingSpot = spotSelectionStrategyByType.getParkingSpot(vehicleType, parkingLot);
        Ticket ticket;
        //create ticket
        try{
            ticket = new Ticket();
            ticket.setEntryTime(new Date());
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicle(vehicle);
            ticket.setGate(gate);
            ticket.setOperator(operator);
            ticket.setSpotNumber(parkingSpot.getSpotNumber());
            ticket.setTicketStatus(TicketStatus.OPEN);

            ticketRepository.save(ticket);
            parkingSpotService.updateSpotStatus(parkingSpot,SpotStatus.BLOCKED,SpotStatus.FILLED);
        }catch (ApplicationException applicationException){
            throw applicationException;
        }
        catch(Exception exception){
            throw new ApplicationException(ResponseCode.PL_500101,"Something Went Wrong,");
        }

        return createTicketDto(gate, operator, parkingSpot, ticket);
    }

    private static TicketDto createTicketDto(Gate gate, Operator operator, ParkingSpot parkingSpot, Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setEntryTime(ticket.getEntryTime());
        ticketDto.setParkingFloor(parkingSpot.getParkingFloor().getFloor());
        ticketDto.setGateNumber(gate.getGateNumber());
        ticketDto.setOperatorId(operator.getEmployeeId().toString());
        ticketDto.setSpotNumber(ticket.getSpotNumber());
        ticketDto.setParkingLotId(gate.getParkingLot().getId());
        return ticketDto;
    }


}
