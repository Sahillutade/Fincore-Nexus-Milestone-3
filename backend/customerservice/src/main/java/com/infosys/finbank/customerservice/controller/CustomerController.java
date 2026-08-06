package com.infosys.finbank.customerservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.finbank.customerservice.model.Customer;
import com.infosys.finbank.customerservice.repository.CustomerRepository;
import com.infosys.finbank.customerservice.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private CustomerService customerService; // Injected the customer service layer

    // 1. REGISTER CUSTOMER (POST)
    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer) {
        return repo.save(customer);
    }

    // 2. VIEW ALL CUSTOMERS (GET)
    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return (List<Customer>) repo.findAll();
    }

    // 3. VIEW SINGLE CUSTOMER PROFILE WITH LINKED ACCOUNTS (GET)
    // Upgraded to coordinate with the Account Microservice via OpenFeign
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerWithAccounts(id);
    }

    // 4. UPDATE CUSTOMER DETAILS (PUT)
    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer updatedData) {
        Customer existingCustomer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));
        
        existingCustomer.setName(updatedData.getName());
        existingCustomer.setAddress(updatedData.getAddress());
        existingCustomer.setKycStatus(updatedData.getKycStatus());
        
        return repo.save(existingCustomer);
    }

    // 5. DELETE CUSTOMER PROFILE (DELETE)
    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Customer profile not found");
        }
        repo.deleteById(id);
        return "Customer profile with ID " + id + " has been deleted safely.";
    }

}
