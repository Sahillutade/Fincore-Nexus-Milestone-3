package com.infosys.finbank.customerservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.customerservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
