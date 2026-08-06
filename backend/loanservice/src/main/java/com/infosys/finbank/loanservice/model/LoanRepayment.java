package com.infosys.finbank.loanservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.infosys.finbank.loanservice.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LoanRepayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer installmentNumber;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private BigDecimal emiAmount;

    private BigDecimal principalComponent;

    private BigDecimal interestComponent;

    private BigDecimal remainingBalance;

    private BigDecimal penaltyAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name="loan_id")
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }

    public BigDecimal getPrincipalComponent() {
        return principalComponent;
    }

    public void setPrincipalComponent(BigDecimal principalComponent) {
        this.principalComponent = principalComponent;
    }

    public BigDecimal getInterestComponent() {
        return interestComponent;
    }

    public void setInterestComponent(BigDecimal interestComponent) {
        this.interestComponent = interestComponent;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public LoanRepayment() {
    }

    public LoanRepayment(Long id, Integer installmentNumber, LocalDate dueDate, LocalDate paymentDate,
            BigDecimal emiAmount, BigDecimal principalComponent, BigDecimal interestComponent,
            BigDecimal remainingBalance, BigDecimal penaltyAmount, PaymentStatus paymentStatus, Loan loan) {
        this.id = id;
        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.emiAmount = emiAmount;
        this.principalComponent = principalComponent;
        this.interestComponent = interestComponent;
        this.remainingBalance = remainingBalance;
        this.penaltyAmount = penaltyAmount;
        this.paymentStatus = paymentStatus;
        this.loan = loan;
    }

}
