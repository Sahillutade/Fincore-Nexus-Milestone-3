package com.infosys.finbank.customerservice.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infosys.finbank.customerservice.client.Account;
import com.infosys.finbank.customerservice.client.AccountFeignClient;
import com.infosys.finbank.customerservice.dto.LoginRequest;
import com.infosys.finbank.customerservice.dto.LoginResponse;
import com.infosys.finbank.customerservice.model.Customer;
import com.infosys.finbank.customerservice.repository.CustomerRepository;
import com.infosys.finbank.customerservice.security.JwtService;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final AccountFeignClient accountFeignClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public CustomerService(CustomerRepository customerRepo,
                       AccountFeignClient accountFeignClient,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.customerRepo = customerRepo;
        this.accountFeignClient = accountFeignClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Customer registerCustomer(Customer customer) {

        if(customerRepo.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email is already registered.");
        }

        if(customerRepo.existsByPhoneNumber(customer.getPhoneNumber())) {
            throw new RuntimeException(
                    "Phone number is already registered."
            );
        }

        customer.setCustid(generateCustomerId());

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customer.setKycStatus("PENDING");

        return customerRepo.save(customer);

    }

    private Long generateCustomerId() {

        Long custid;

        do {
            custid = ThreadLocalRandom.current().nextLong(100000L, 1000000L);
        }
        while(customerRepo.existsById(custid));

        return custid;

    }

    public Customer getCustomerWithAccounts(Long id) {

        Customer customer = customerRepo.findById(id)
        .orElseThrow(() -> 
            new RuntimeException("Customer not found.")
        );
        
        try {

            List<Account> linkedAccounts = accountFeignClient.getAccountsByCustomerId(id);

            customer.setAccounts(linkedAccounts);

        }
        catch (Exception e) {
            System.out.println(
                ">>> Account Service unavailable. " +
                "Returning customer profile without accounts."
            );
        }

        return customer;

    }

    public Customer updateCustomer(Long id, Customer updatedData) {

        Customer existingCustomer = customerRepo.findById(id)
            .orElseThrow(() ->
                new RuntimeException(
                    "Customer profile not found."
                )
            );

        existingCustomer.setFullName(updatedData.getFullName());

        existingCustomer.setEmail(updatedData.getEmail());

        existingCustomer.setPhoneNumber(updatedData.getPhoneNumber());

        existingCustomer.setProfilePhoto(updatedData.getProfilePhoto());

        return customerRepo.save(existingCustomer);

    }

    public void deleteCustomer(Long id) {

        if(!customerRepo.existsById(id)) {
            throw new RuntimeException(
                "Customer profile not found."
            );
        }

        customerRepo.deleteById(id);

    }

    public LoginResponse login(LoginRequest request) {

    Customer customer = customerRepo
            .findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new RuntimeException(
                            "Invalid email or password."
                    )
            );

    boolean passwordMatches =
            passwordEncoder.matches(
                    request.getPassword(),
                    customer.getPassword()
            );

    if (!passwordMatches) {
        throw new RuntimeException(
                "Invalid email or password."
        );
    }

    String token = jwtService.generateToken(
            customer.getCustid(),
            customer.getEmail()
    );

    return new LoginResponse(
            "Login successful",
            customer.getCustid(),
            customer.getFullName(),
            customer.getEmail(),
            token
    );
}

}
