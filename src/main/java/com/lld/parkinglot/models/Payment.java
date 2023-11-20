package com.lld.parkinglot.models;

import com.lld.parkinglot.enums.PaymentMode;
import com.lld.parkinglot.enums.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
@Data
public class Payment extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode",nullable = false)
    private PaymentMode paymentMode;

    @ManyToOne
    @JoinColumn(name = "payment_bill_id")
    private Bill bill;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status",nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_success_at")
    private Date paymentSuccessAt;
}
