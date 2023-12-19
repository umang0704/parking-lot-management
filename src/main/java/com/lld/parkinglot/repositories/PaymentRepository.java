package com.lld.parkinglot.repositories;

import com.lld.parkinglot.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
