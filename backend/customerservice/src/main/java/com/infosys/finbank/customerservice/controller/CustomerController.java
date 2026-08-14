package com.infosys.finbank.customerservice.controller;

import java.util.List;
import java.util.UUID;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.finbank.customerservice.dto.LoginRequest;
import com.infosys.finbank.customerservice.dto.LoginResponse;
import com.infosys.finbank.customerservice.model.Customer;
import com.infosys.finbank.customerservice.repository.CustomerRepository;
import com.infosys.finbank.customerservice.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepo;
    private final CustomerService customerService;
    public CustomerController(CustomerRepository customerRepo, CustomerService customerService) {
        this.customerRepo = customerRepo;
        this.customerService = customerService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.registerCustomer(customer);

        return ResponseEntity.status(HttpStatus.SC_CREATED).body(savedCustomer);

    } 

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {

        return ResponseEntity.ok(
                customerRepo.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable("id") Long id) {

        Customer customer =
                customerService.getCustomerWithAccounts(id);

        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody Customer updatedData) {

        Customer updatedCustomer =
                customerService.updateCustomer(
                        id,
                        updatedData
                );

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable("id") Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                "Customer profile with ID "
                + id
                + " has been deleted successfully."
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody LoginRequest request) {

        LoginResponse response =customerService.login(request);

        return ResponseEntity.ok(response);
    }

}
