package com.infosys.finbank.account.dto;

import java.util.UUID;

public class CustomerDto {

    private UUID custid;
    private String name;
    private String address;
    private String kycStatus;

    public CustomerDto() {
    }

    public UUID getCustid() {
        return custid;
    }

    public void setCustid(UUID custid) {
        this.custid = custid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }
}