package com.infosys.finbank.customerservice.service;

import org.springframework.stereotype.Component;

import com.infosys.finbank.customerservice.model.Customer;
import com.infosys.finbank.customerservice.repository.CustomerRepository;

import jakarta.annotation.PostConstruct;

@Component
public class CustomerDataLoader {

    private final CustomerRepository repo;

    public CustomerDataLoader(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void loadDummyData() {
        if (repo.count() == 0) {
            // Using our custom constructor matching the expanded entity variables
            repo.save(new Customer("John Smith", "123 Financial District", "Verified"));
            repo.save(new Customer("Jane Doe", "456 Commerce Avenue", "Pending"));
            repo.save(new Customer("Nikhil Kumar", "789 Tech Boulevard", "Verified"));
            
            System.out.println(">>> FinCore Production-Grade Customer Data Loaded Successfully!");
        }
    }

}
