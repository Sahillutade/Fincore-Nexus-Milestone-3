package com.infosys.finbank.loanservice.service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.finbank.loanservice.enums.LoanStatus;
import com.infosys.finbank.loanservice.enums.LoanType;
import com.infosys.finbank.loanservice.enums.NPAStatus;
import com.infosys.finbank.loanservice.enums.SagaStatus;
import com.infosys.finbank.loanservice.enums.SagaStep;
import com.infosys.finbank.loanservice.model.CreditAssessment;
import com.infosys.finbank.loanservice.enums.DisbursementStatus;
import com.infosys.finbank.loanservice.model.Loan;
import com.infosys.finbank.loanservice.model.LoanDisbursement;
import com.infosys.finbank.loanservice.model.LoanRepayment;
import com.infosys.finbank.loanservice.model.LoanSaga;
import com.infosys.finbank.loanservice.repository.CreditAssessmentRepository;
import com.infosys.finbank.loanservice.repository.LoanDisbursementRepository;
import com.infosys.finbank.loanservice.repository.LoanRepaymentRepository;
import com.infosys.finbank.loanservice.repository.LoanRepository;
import com.infosys.finbank.loanservice.repository.LoanSagaRepository;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CreditAssessmentRepository creditAssessmentRepository;

    @Autowired
    private LoanDisbursementRepository loanDisbursementRepository;

    @Autowired
    private LoanRepaymentRepository loanRepaymentRepository;

    @Autowired
    private LoanSagaRepository loanSagaRepository;

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }
    public Loan updateLoan(Loan loan) {
        return loanRepository.save(loan);
    }
    public String deleteLoan(Long id) {
        loanRepository.deleteById(id);
        return "Loan Deleted Successfully";
    }
    public Loan getLoanByLoanNumber(String loanNumber) {
        return loanRepository.findByLoanNumber(loanNumber).orElse(null);
    }
    public List<Loan> getLoansByCustomer(UUID customerId) {
        return loanRepository.findByCustomerId(customerId);
    }
    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByLoanStatus(status);
    }
    public List<Loan> getLoansByType(LoanType type) {
        return loanRepository.findByLoanType(type);
    }
    public List<Loan> getLoansByAutoDebit(Boolean enabled) {
        return loanRepository.findByAutoDebitEnabled(enabled);
    }
    public List<Loan> getLoansByNPAStatus(NPAStatus status) {
        return loanRepository.findByNpaStatus(status);
    }

    public CreditAssessment assessCredit(Long loanId, CreditAssessment assessment) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        assessment.setLoan(loan);
        loan.setCreditAssessment(assessment);
        assessment.setAssessmentDate(LocalDate.now());

        if (assessment.getCreditScore() >= 750 && assessment.getEligibleAmount().compareTo(loan.getPrincipalAmount()) >= 0) {

            assessment.setApproved(true);
            assessment.setRemarks("Credit Assessment Passed");

            loan.setLoanStatus(LoanStatus.CREDIT_CHECK);

        } else {

            assessment.setApproved(false);
            assessment.setRemarks("Credit Assessment Failed");

            loan.setLoanStatus(LoanStatus.REJECTED);
        }

        loanRepository.save(loan);

        return creditAssessmentRepository.save(assessment);
    }
    public Loan calculateEMI(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        double principal = loan.getPrincipalAmount().doubleValue();

        double annualRate = loan.getInterestRate().doubleValue();

        double monthlyRate = annualRate / (12 * 100);

        int months = loan.getTenureMonths();

        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);

        loan.setEmiAmount(BigDecimal.valueOf(emi));

        BigDecimal totalRepayable = BigDecimal.valueOf(emi * months);

        loan.setTotalRepayableAmount(totalRepayable);

        loan.setTotalInterest(
                totalRepayable.subtract(loan.getPrincipalAmount())
        );

        loan.setOutstandingAmount(totalRepayable);

        return loanRepository.save(loan);
    }

    public Loan approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        loan.setLoanStatus(LoanStatus.APPROVED);

        loan.setSanctionDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public Loan rejectLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        loan.setLoanStatus(LoanStatus.REJECTED);

        return loanRepository.save(loan);
    }

    public LoanSaga startLoanSaga(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        LoanSaga saga = new LoanSaga();

        saga.setLoan(loan);
        loan.setSaga(saga);
        saga.setCurrentStep(SagaStep.LOAN_SERVICE);
        saga.setStatus(SagaStatus.STARTED);
        saga.setCreatedAt(LocalDateTime.now());

        return loanSagaRepository.save(saga);
    }

    public LoanDisbursement disburseLoan(Long loanId, LoanDisbursement disbursement) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        loan.setLoanStatus(LoanStatus.DISBURSEMENT_IN_PROGRESS);

        disbursement.setLoan(loan);
        disbursement.setDisbursementDate(LocalDate.now());
        disbursement.setStatus(DisbursementStatus.COMPLETED);

        loan.setDisbursementDate(LocalDate.now());
        loan.setLoanStatus(LoanStatus.ACTIVE);
        loan.setDisbursement(disbursement);

        loanRepository.save(loan);

        return loanDisbursementRepository.save(disbursement);
    }

    public List<LoanRepayment> getRepaymentHistory(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        return loanRepaymentRepository.findByLoanIdOrderByInstallmentNumber(loanId);
    }

    public Loan classifyNPA(Long loanId, NPAStatus status) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        loan.setNpaStatus(status);

        if (status == NPAStatus.LOSS) {
            loan.setLoanStatus(LoanStatus.DEFAULTED);
        }

        return loanRepository.save(loan);
    }

    public LoanSaga getSagaStatus(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return null;
        }

        return loanSagaRepository.findByLoanId(loanId).orElse(null);
    }
}