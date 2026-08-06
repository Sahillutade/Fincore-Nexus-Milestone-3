package com.infosys.finbank.loanservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.infosys.finbank.loanservice.enums.LoanStatus;
import com.infosys.finbank.loanservice.enums.LoanType;
import com.infosys.finbank.loanservice.enums.NPAStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(unique = true, nullable = false)
    private String loanNumber;

    private UUID customerId;
    private UUID accountId;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private BigDecimal emiAmount;
    private BigDecimal processingFee;
    private BigDecimal outstandingAmount;
    private BigDecimal totalInterest;
    private BigDecimal totalRepayableAmount;
    private LocalDate sanctionDate;
    private LocalDate disbursementDate;
    private LocalDate nextEmiDate;
    private LocalDate loanEndDate;
    private Boolean autoDebitEnabled;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @Enumerated(EnumType.STRING)
    private NPAStatus npaStatus;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    private CreditAssessment creditAssessment;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    private LoanDisbursement disbursement;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    private LoanSaga saga;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<LoanRepayment> repayments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }

    public BigDecimal getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(BigDecimal processingFee) {
        this.processingFee = processingFee;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public BigDecimal getTotalRepayableAmount() {
        return totalRepayableAmount;
    }

    public void setTotalRepayableAmount(BigDecimal totalRepayableAmount) {
        this.totalRepayableAmount = totalRepayableAmount;
    }

    public LocalDate getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(LocalDate sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public LocalDate getNextEmiDate() {
        return nextEmiDate;
    }

    public void setNextEmiDate(LocalDate nextEmiDate) {
        this.nextEmiDate = nextEmiDate;
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Boolean getAutoDebitEnabled() {
        return autoDebitEnabled;
    }

    public void setAutoDebitEnabled(Boolean autoDebitEnabled) {
        this.autoDebitEnabled = autoDebitEnabled;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public NPAStatus getNpaStatus() {
        return npaStatus;
    }

    public void setNpaStatus(NPAStatus npaStatus) {
        this.npaStatus = npaStatus;
    }

    public CreditAssessment getCreditAssessment() {
        return creditAssessment;
    }

    public void setCreditAssessment(CreditAssessment creditAssessment) {
        this.creditAssessment = creditAssessment;
    }

    public LoanDisbursement getDisbursement() {
        return disbursement;
    }

    public void setDisbursement(LoanDisbursement disbursement) {
        this.disbursement = disbursement;
    }

    public LoanSaga getSaga() {
        return saga;
    }

    public void setSaga(LoanSaga saga) {
        this.saga = saga;
    }

    public List<LoanRepayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<LoanRepayment> repayments) {
        this.repayments = repayments;
    }

    public Loan() {
    }

    public Loan(Long id, String loanNumber, UUID customerId, UUID accountId, LoanType loanType,
            BigDecimal principalAmount, BigDecimal interestRate, Integer tenureMonths, BigDecimal emiAmount,
            BigDecimal processingFee, BigDecimal outstandingAmount, BigDecimal totalInterest,
            BigDecimal totalRepayableAmount, LocalDate sanctionDate, LocalDate disbursementDate, LocalDate nextEmiDate,
            LocalDate loanEndDate, Boolean autoDebitEnabled, LoanStatus loanStatus, NPAStatus npaStatus,
            CreditAssessment creditAssessment, LoanDisbursement disbursement, LoanSaga saga,
            List<LoanRepayment> repayments) {
        this.id = id;
        this.loanNumber = loanNumber;
        this.customerId = customerId;
        this.accountId = accountId;
        this.loanType = loanType;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.emiAmount = emiAmount;
        this.processingFee = processingFee;
        this.outstandingAmount = outstandingAmount;
        this.totalInterest = totalInterest;
        this.totalRepayableAmount = totalRepayableAmount;
        this.sanctionDate = sanctionDate;
        this.disbursementDate = disbursementDate;
        this.nextEmiDate = nextEmiDate;
        this.loanEndDate = loanEndDate;
        this.autoDebitEnabled = autoDebitEnabled;
        this.loanStatus = loanStatus;
        this.npaStatus = npaStatus;
        this.creditAssessment = creditAssessment;
        this.disbursement = disbursement;
        this.saga = saga;
        this.repayments = repayments;
    }

}
