package com.infosys.finbank.account.service;

import com.infosys.finbank.account.dto.CustomerDto;
import com.infosys.finbank.account.dto.TransactionDto;
import com.infosys.finbank.account.feign.CustomerFeignClient;
import com.infosys.finbank.account.feign.TransactionFeignClient;
import com.infosys.finbank.account.model.Account;
import com.infosys.finbank.account.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    @Autowired
    private CustomerFeignClient customerFeignClient;

    @Autowired
    private TransactionFeignClient transactionFeignClient;

    // Add Account
    public Account addAccount(Account account) {
        return repo.save(account);
    }

    // View All Accounts
    public Iterable<Account> getAllAccounts() {
        return repo.findAll();
    }

    // View Account By Id
    public Account getAccount(Integer id) {
        Optional<Account> account = repo.findById(id);

        if (account.isPresent()) {
            return account.get();
        }

        return null;
    }

    // Update Account
    public Account updateAccount(Account account) {
        return repo.save(account);
    }

    // Delete Account
    public String deleteAccount(Integer id) {
        repo.deleteById(id);
        return "Account Deleted Successfully";
    }

    // Deposit Money
    public Account deposit(Integer id, Double amount) {

        Optional<Account> account = repo.findById(id);

        if (account.isPresent()) {

            Account acc = account.get();
            acc.setBalance(acc.getBalance() + amount);

            return repo.save(acc);
        }

        return null;
    }

    // Withdraw Money
    public Account withdraw(Integer id, Double amount) {

        Optional<Account> account = repo.findById(id);

        if (account.isPresent()) {

            Account acc = account.get();

            if (acc.getBalance() >= amount) {

                acc.setBalance(acc.getBalance() - amount);

                return repo.save(acc);
            }
        }

        return null;
    }

    // Check Balance
    public Double getBalance(Integer id) {

        Optional<Account> account = repo.findById(id);

        if (account.isPresent()) {
            return account.get().getBalance();
        }

        return 0.0;
    }

    // Feign - Customer Service
    public CustomerDto getCustomer(UUID id) {
        return customerFeignClient.getCustomer(id);
    }

    // Feign - Transaction Service
    public List<TransactionDto> getTransactions(Long accountId) {
        return transactionFeignClient.getTransactions(accountId);
    }
}