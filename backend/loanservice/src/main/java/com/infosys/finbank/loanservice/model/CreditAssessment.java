package com.infosys.finbank.loanservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CreditAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer creditScore;

    private BigDecimal monthlyIncome;

    private BigDecimal existingLiability;

    private BigDecimal eligibleAmount;

    private Boolean approved;

    private String remarks;

    private LocalDate assessmentDate;

    @OneToOne
    @JoinColumn(name="loan_id")
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getExistingLiability() {
        return existingLiability;
    }

    public void setExistingLiability(BigDecimal existingLiability) {
        this.existingLiability = existingLiability;
    }

    public BigDecimal getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(BigDecimal eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public CreditAssessment() {
    }

    public CreditAssessment(Long id, Integer creditScore, BigDecimal monthlyIncome, BigDecimal existingLiability,
            BigDecimal eligibleAmount, Boolean approved, String remarks, LocalDate assessmentDate, Loan loan) {
        this.id = id;
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.existingLiability = existingLiability;
        this.eligibleAmount = eligibleAmount;
        this.approved = approved;
        this.remarks = remarks;
        this.assessmentDate = assessmentDate;
        this.loan = loan;
    }

}
