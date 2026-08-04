package com.infosys.finbank.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.infosys.finbank.payment.dto.PaymentRequestDTO;
import com.infosys.finbank.payment.model.Beneficiary;
import com.infosys.finbank.payment.model.Payment;
import com.infosys.finbank.payment.repository.BeneficiaryRepository;
import com.infosys.finbank.payment.repository.PaymentRepository;
import com.infosys.finbank.payment.service.FraudService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final FraudService fraudService;

    public PaymentController(PaymentRepository paymentRepository, 
                             BeneficiaryRepository beneficiaryRepository, 
                             FraudService fraudService) {
        this.paymentRepository = paymentRepository;
        this.beneficiaryRepository = beneficiaryRepository;
        this.fraudService = fraudService;
    }

    // 1. Fetch All Payments
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 2. Beneficiary Management Endpoints[cite: 1]
    @GetMapping("/beneficiary/all")
    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }

    @PostMapping("/beneficiary/add")
    public ResponseEntity<Beneficiary> addBeneficiary(@RequestBody Beneficiary beneficiary) {
        beneficiary.setVerified(true);
        Beneficiary saved = beneficiaryRepository.save(beneficiary);
        return ResponseEntity.ok(saved);
    }

    // 3. Fund Transfer Endpoint[cite: 1]
    @PostMapping("/transfer")
    public ResponseEntity<?> processTransfer(@RequestBody PaymentRequestDTO dto) {
        // Check 1: Beneficiary Account Verification[cite: 1]
        Optional<Beneficiary> beneficiaryOpt = beneficiaryRepository.findByAccountNumber(dto.getToAccountId());
        if (beneficiaryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "status", "FAILED",
                        "error", "Beneficiary account '" + dto.getToAccountId() + "' not registered in beneficiary directory."
                    ));
        }

        // Check 2: Calculate Fraud Risk Score[cite: 1]
        double riskScore = fraudService.calculateRiskScore(dto.getAmount());
        boolean isFraud = fraudService.isFraudulent(riskScore);

        Payment payment = new Payment();
        payment.setFromAccountId(dto.getFromAccountId());
        payment.setToAccountId(dto.getToAccountId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentType(dto.getPaymentType() != null ? dto.getPaymentType() : "IMPS");
        payment.setRiskScore(riskScore);
        payment.setTimestamp(LocalDateTime.now());

        // Generate UTR Reference Number[cite: 1]
        String prefix = payment.getPaymentType().toUpperCase();
        payment.setUtrNumber(prefix + (System.currentTimeMillis() % 1000000000));

        if (isFraud) {
            payment.setStatus("FAILED_FRAUD_RISK");
            paymentRepository.save(payment);
            return ResponseEntity.ok(Map.of(
                "status", "FAILED_FRAUD_RISK",
                "message", "Transaction blocked due to high fraud risk score.",
                "riskScore", riskScore,
                "utrNumber", payment.getUtrNumber()
            ));
        }

        payment.setStatus("SUCCESS");
        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
    }
}