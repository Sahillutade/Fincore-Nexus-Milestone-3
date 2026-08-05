package com.infosys.finbank.payment.service;

import org.springframework.stereotype.Service;

@Service
public class FraudService {

    public double calculateRiskScore(Double amount) {
        if (amount == null) return 0.0;
        
        // Dynamic Risk Assessment
        if (amount > 100000.0) {
            return 0.85; // High Risk
        } else if (amount > 50000.0) {
            return 0.45; // Medium Risk
        } else {
            return 0.12; // Low Risk
        }
    }

    public boolean isFraudulent(double riskScore) {
        return riskScore >= 0.80; // Flag payments with risk score >= 0.80[cite: 1]
    }
}