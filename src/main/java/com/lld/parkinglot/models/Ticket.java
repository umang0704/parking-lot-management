package com.lld.parkinglot.models;


import com.lld.parkinglot.enums.TicketStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
@Data
public class Ticket extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "entry_time",nullable = false)
    private Date entryTime;

    @ManyToOne
    @JoinColumn(name = "ticket_vehicle_id",nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "ticket_gate_id",nullable = false)
    private Gate gate;

    @ManyToOne
    @JoinColumn(name = "ticket_operator_id",nullable = false)
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "ticket_parking_spot_id",nullable = false)
    private ParkingSpot parkingSpot;

    @Column(name = "spot_number",nullable = false)
    private Integer spotNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status",nullable = false)
    private TicketStatus ticketStatus;


}
