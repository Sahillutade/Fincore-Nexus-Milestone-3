package com.infosys.finbank.customerservice.model;

import java.util.List;
import java.util.UUID;

import com.infosys.finbank.customerservice.client.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID custid;
    
    private String name;
    private String address;
    private String kycStatus;

    @Transient// Ignores this field during H2 database table creation
    private List<Account> accounts; // Fixed: Changed from List<Object> to List<Account>

    public Customer() {}

    public Customer(String name, String address, String kycStatus) {
        this.name = name;
        this.address = address;
        this.kycStatus = kycStatus;
    }

    // Standard Java Getters and Setters
    public UUID getCustid() { return custid; }
    public void setCustid(UUID custid) { this.custid = custid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }

    // Fixed Getter and Setter Types
    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }

}