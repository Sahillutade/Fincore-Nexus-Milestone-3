package com.infosys.finbank.transactionservice.service;

import java.util.List;
import java.util.UUID;

import com.infosys.finbank.transactionservice.dto.TransactionRequest;
import com.infosys.finbank.transactionservice.dto.TransactionResponse;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse getTransaction(Long id);

    List<TransactionResponse> getAllTransactions();

    List<TransactionResponse> getTransactionsByAccount(Long accoutnId);

    List<TransactionResponse> getTransactionsByCustomer(UUID customerId);

}
