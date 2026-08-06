package com.infosys.finbank.loanservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.infosys.finbank.loanservice.enums.DisbursementStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class LoanDisbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String transactionReference;

    private LocalDate disbursementDate;

    @Enumerated(EnumType.STRING)
    private DisbursementStatus status;

    @OneToOne
    @JoinColumn(name="loan_id")
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public DisbursementStatus getStatus() {
        return status;
    }

    public void setStatus(DisbursementStatus status) {
        this.status = status;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public LoanDisbursement() {
    }

    public LoanDisbursement(Long id, BigDecimal amount, String transactionReference, LocalDate disbursementDate,
            DisbursementStatus status, Loan loan) {
        this.id = id;
        this.amount = amount;
        this.transactionReference = transactionReference;
        this.disbursementDate = disbursementDate;
        this.status = status;
        this.loan = loan;
    }

}
