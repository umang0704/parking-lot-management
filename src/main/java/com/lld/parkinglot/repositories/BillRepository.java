package com.lld.parkinglot.repositories;

import com.lld.parkinglot.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {}
