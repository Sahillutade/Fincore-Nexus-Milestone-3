package com.infosys.finbank.transactionservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.transactionservice.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByCustomerId(UUID customerId);

    Optional<Transaction> findByTransactionReference(String reference);

    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);

}
