package com.infosys.finbank.transactionservice.client.dto;

import java.util.UUID;

public class CustomerResponse {

    private UUID customerId;
    private String name;
    private String address;
    private String kycStatus;

    public UUID getCustomerId() {
        return customerId;
    }
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
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
