package com.lld.parkinglot.repositories;

import com.lld.parkinglot.enums.TicketStatus;
import com.lld.parkinglot.models.Ticket;
import com.lld.parkinglot.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> getByTicketStatusAndVehicle(TicketStatus ticketStatus, Vehicle vehicle);

    Ticket findByVehicleAndTicketStatus(Vehicle vehicle, TicketStatus ticketStatus);


}