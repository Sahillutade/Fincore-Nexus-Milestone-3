package com.infosys.finbank.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infosys.finbank.payment.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}