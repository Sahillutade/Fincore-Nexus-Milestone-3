package com.infosys.finbank.customerservice.client;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private UUID accid;        // Account Unique ID matching the FinCore system spec
    private String type;       // e.g., "Savings" or "Current"
    private BigDecimal balance; // Bank account balance

    // Empty Constructor
    public Account() {}

    // Getters and Setters
    public UUID getAccid() { return accid; }
    public void setAccid(UUID accid) { this.accid = accid; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

}
