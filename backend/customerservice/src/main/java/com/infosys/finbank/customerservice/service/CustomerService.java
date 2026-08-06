package com.infosys.finbank.customerservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.finbank.customerservice.client.Account;
import com.infosys.finbank.customerservice.client.AccountFeignClient;
import com.infosys.finbank.customerservice.model.Customer;
import com.infosys.finbank.customerservice.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountFeignClient accountFeignClient;

    public Customer getCustomerWithAccounts(UUID id) {
        // 1. Grab the base customer info from your local H2 Database [cite: 472]
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        try {
            // 2. Reach out over the local network via Feign to pull accounts [cite: 473, 474]
            List<Account> linkedAccounts = accountFeignClient.getAccountsByCustomerId(id);
            
            // 3. Temporarily append those accounts onto our local response layout [cite: 475]
            customer.setAccounts(linkedAccounts);
        } catch (Exception e) {
            // Safe fallback logic: If your teammate's service is turned off, the app won't crash!
            System.out.println(">>> Account service down. Returning base customer metadata profile.");
        }
        
        return customer;
    }

}
