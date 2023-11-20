package com.lld.parkinglot.repositories;

import com.lld.parkinglot.enums.TicketStatus;
import com.lld.parkinglot.models.Ticket;
import com.lld.parkinglot.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByVehicleAndTicketStatus(Vehicle vehicle, TicketStatus ticketStatus);


}