package com.infosys.finbank.transactionservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infosys.finbank.transactionservice.client.AccountFeignClient;
import com.infosys.finbank.transactionservice.client.CustomerFeignClient;
import com.infosys.finbank.transactionservice.client.dto.AccountResponse;
import com.infosys.finbank.transactionservice.client.dto.CustomerResponse;
import com.infosys.finbank.transactionservice.dto.TransactionRequest;
import com.infosys.finbank.transactionservice.dto.TransactionResponse;
import com.infosys.finbank.transactionservice.enums.TransactionStatus;
import com.infosys.finbank.transactionservice.model.Transaction;
import com.infosys.finbank.transactionservice.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final AccountFeignClient accountFeignClient;
    private final CustomerFeignClient customerFeignClient;

    public TransactionServiceImpl(TransactionRepository repository, AccountFeignClient accountFeignClient,
            CustomerFeignClient customerFeignClient) {
        this.repository = repository;
        this.accountFeignClient = accountFeignClient;
        this.customerFeignClient = customerFeignClient;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {

        CustomerResponse customer = customerFeignClient.getCustomerById(request.getCustomerId());

        AccountResponse account = accountFeignClient.getAccountById(request.getAccountId());

        if(customer == null) {
            throw new RuntimeException("Customer not found");
        }

        if(account == null) {
            throw new RuntimeException("Account not found");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setAccountId(request.getAccountId());
        transaction.setCustomerId(request.getCustomerId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setRemarks(request.getRemarks());
        transaction.setBalanceAfterTransaction(account.getBalance());

        Transaction saved = repository.save(transaction);

        return mapToResponse(saved);

    }

    @Override
    public TransactionResponse getTransaction(Long id) {

        Transaction transaction = repository.findById(id)
        .orElseThrow(() -> 
            new RuntimeException("Transaction not found")
        );

        return mapToResponse(transaction);

    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        
        return repository.findAll()
        .stream()
        .map(this::mapToResponse)
        .toList();

    }

    @Override
    public List<TransactionResponse> getTransactionsByAccount(Long accountId) {

        return repository.findByAccountId(accountId)
        .stream()
        .map(this::mapToResponse)
        .toList();

    }

    @Override
    public List<TransactionResponse> getTransactionsByCustomer(UUID customerId) {

        return repository.findByCustomerId(customerId)
        .stream()
        .map(this::mapToResponse)
        .toList();

    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setTransactionReference(transaction.getTransactionReference());
        response.setAccountId(transaction.getAccountId());
        response.setCustomerId(transaction.getCustomerId());
        response.setAmount(transaction.getAmount());
        response.setBalanceAfterTransaction(transaction.getBalanceAfterTransaction());
        response.setTransactionType(transaction.getTransactionType());
        response.setStatus(transaction.getStatus());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setRemarks(transaction.getRemarks());

        return response;

    }

}
