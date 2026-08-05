package com.infosys.finbank.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infosys.finbank.payment.dto.PaymentRequestDTO;
import com.infosys.finbank.payment.model.Beneficiary;
import com.infosys.finbank.payment.model.Payment;
import com.infosys.finbank.payment.repository.BeneficiaryRepository;
import com.infosys.finbank.payment.repository.PaymentRepository;
import com.infosys.finbank.payment.service.FraudService;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

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

    // Razorpay Keys injected from application.properties
    @Value("${razorpay.key.id:rzp_test_placeholder}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret:placeholder_secret}")
    private String razorpayKeySecret;

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

    // 2. Beneficiary Management Endpoints
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

    // 3. Fund Transfer Endpoint
    @PostMapping("/transfer")
    public ResponseEntity<?> processTransfer(@RequestBody PaymentRequestDTO dto) {
        // Check 1: Beneficiary Account Verification
        Optional<Beneficiary> beneficiaryOpt = beneficiaryRepository.findByAccountNumber(dto.getToAccountId());
        if (beneficiaryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "status", "FAILED",
                        "error", "Beneficiary account '" + dto.getToAccountId() + "' not registered in beneficiary directory."
                    ));
        }

        // Check 2: Calculate Fraud Risk Score
        double riskScore = fraudService.calculateRiskScore(dto.getAmount());
        boolean isFraud = fraudService.isFraudulent(riskScore);

        Payment payment = new Payment();
        payment.setFromAccountId(dto.getFromAccountId());
        payment.setToAccountId(dto.getToAccountId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentType(dto.getPaymentType() != null ? dto.getPaymentType() : "IMPS");
        payment.setRiskScore(riskScore);
        payment.setTimestamp(LocalDateTime.now());

        // Generate UTR Reference Number
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

    // 4. NEW: Razorpay Order Creation Endpoint
    @PostMapping("/create-order")
    public ResponseEntity<?> createRazorpayOrder(@RequestBody Map<String, Object> data) {
        try {
            double amountInRupees = Double.parseDouble(data.get("amount").toString());
            int amountInPaise = (int) (amountInRupees * 100); // Converts ₹ to paise

            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = razorpayClient.orders.create(orderRequest);

            return ResponseEntity.ok(Map.of(
                "orderId", order.get("id"),
                "currency", order.get("currency"),
                "amount", order.get("amount"),
                "key", razorpayKeyId
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Razorpay order creation failed: " + e.getMessage()));
        }
    }
}