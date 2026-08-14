package com.infosys.finbank.customerservice.model;

import java.util.List;

import com.infosys.finbank.customerservice.client.Account;

import jakarta.persistence.Column;
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
    @Column(name = "custid", unique = true, nullable = false)
    private Long custid;
    
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePhoto;
    private String kycStatus;

    @Transient// Ignores this field during H2 database table creation
    private List<Account> accounts; // Fixed: Changed from List<Object> to List<Account>

    public Customer() {}

    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Customer(Long custid, String fullName, String email, String phoneNumber, String password,
            String profilePhoto, String kycStatus, List<Account> accounts) {
        this.custid = custid;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.kycStatus = kycStatus;
        this.accounts = accounts;
    }

}