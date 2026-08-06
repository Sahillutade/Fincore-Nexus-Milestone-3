package com.infosys.finbank.transactionservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.finbank.transactionservice.dto.TransactionRequest;
import com.infosys.finbank.transactionservice.dto.TransactionResponse;
import com.infosys.finbank.transactionservice.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody TransactionRequest request) {

        return ResponseEntity.ok(service.createTransaction(request));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> get(@PathVariable Long id) {

        return ResponseEntity.ok(service.getTransaction(id));

    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponse>> getAll() {

        return ResponseEntity.ok(service.getAllTransactions());

    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getByAccount(@PathVariable Long accountId) {

        return ResponseEntity.ok(service.getTransactionsByAccount(accountId));

    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionResponse>> getByCustomer(@PathVariable UUID customerId) {

        return ResponseEntity.ok(service.getTransactionsByCustomer(customerId));

    }

}
