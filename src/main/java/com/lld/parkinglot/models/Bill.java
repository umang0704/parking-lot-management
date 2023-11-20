package com.lld.parkinglot.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill")
@Data
public class Bill extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_ticket_id",referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "exit_time", nullable = false)
    private Date exitTime;

    @ManyToOne
    @JoinColumn(name = "bill_gate_id")
    private Gate gate;

    @ManyToOne
    @JoinColumn(name = "bill_operator_id")
    private Operator operator;

    @Column(name = "payment_link", nullable = false)
    private String paymentLink;

    @Column(name = "amount_part_whole", nullable = false)
    private Integer amountPartWhole;

    @Column(name = "amount_part_decimal", nullable = false)
    private Integer amountPartDecimal;

}
