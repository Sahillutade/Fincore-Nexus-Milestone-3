package com.infosys.finbank.transactionservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.infosys.finbank.transactionservice.enums.TransactionType;

public class TransactionRequest {

    private Long accountId;

    private UUID customerId;

    private BigDecimal amount;

    private TransactionType transactionType;

    private String remarks;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
