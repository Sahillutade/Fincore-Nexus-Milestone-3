package com.infosys.finbank.loanservice.model;

import java.time.LocalDateTime;

import com.infosys.finbank.loanservice.enums.SagaStatus;
import com.infosys.finbank.loanservice.enums.SagaStep;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class LoanSaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SagaStep currentStep;

    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    private String failureReason;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="loan_id")
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SagaStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(SagaStep currentStep) {
        this.currentStep = currentStep;
    }

    public SagaStatus getStatus() {
        return status;
    }

    public void setStatus(SagaStatus status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public LoanSaga() {
    }

    public LoanSaga(Long id, SagaStep currentStep, SagaStatus status, String failureReason, LocalDateTime createdAt,
            Loan loan) {
        this.id = id;
        this.currentStep = currentStep;
        this.status = status;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        this.loan = loan;
    }

}
