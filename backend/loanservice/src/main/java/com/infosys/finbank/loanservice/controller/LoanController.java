package com.infosys.finbank.loanservice.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.finbank.loanservice.enums.LoanStatus;
import com.infosys.finbank.loanservice.enums.LoanType;
import com.infosys.finbank.loanservice.enums.NPAStatus;
import com.infosys.finbank.loanservice.model.CreditAssessment;
import com.infosys.finbank.loanservice.model.Loan;
import com.infosys.finbank.loanservice.model.LoanDisbursement;
import com.infosys.finbank.loanservice.model.LoanRepayment;
import com.infosys.finbank.loanservice.model.LoanSaga;
import com.infosys.finbank.loanservice.service.LoanService;

@RestController
@RequestMapping("/loan")
@CrossOrigin("*")
public class LoanController{

    @Autowired
    private LoanService service;

    @PostMapping("/add")
    public Loan addLoan(@RequestBody Loan loan) {
        return service.createLoan(loan);
    }

    @GetMapping("/all")
    public List<Loan> getAllLoans() {
        return service.getAllLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return service.getLoanById(id);
    }

    @PutMapping("/update")
    public Loan updateLoan(@RequestBody Loan loan) {
        return service.updateLoan(loan);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        return service.deleteLoan(id);
    }

    @GetMapping("/number/{loanNumber}")
    public Loan getLoanByNumber(@PathVariable String loanNumber) {
        return service.getLoanByLoanNumber(loanNumber);
    }

    @GetMapping("/customer/{customerId}")
    public List<Loan> getLoansByCustomer(@PathVariable UUID customerId) {
        return service.getLoansByCustomer(customerId);
    }

    @GetMapping("/status/{status}")
    public List<Loan> getLoansByStatus(@PathVariable LoanStatus status) {
        return service.getLoansByStatus(status);
    }

    @GetMapping("/type/{type}")
    public List<Loan> getLoansByType(@PathVariable LoanType type) {
        return service.getLoansByType(type);
    }

    @GetMapping("/autodebit/{enabled}")
    public List<Loan> getLoansByAutoDebit(@PathVariable Boolean enabled) {
        return service.getLoansByAutoDebit(enabled);
    }

    @GetMapping("/npa/{status}")
    public List<Loan> getLoansByNPAStatus(@PathVariable NPAStatus status) {
        return service.getLoansByNPAStatus(status);
    }

    @PostMapping("/credit-assessment/{loanId}")
    public CreditAssessment assessCredit(@PathVariable Long loanId,@RequestBody CreditAssessment assessment) {
        return service.assessCredit(loanId, assessment);
    }

    @PostMapping("/calculate-emi/{loanId}")
    public Loan calculateEMI(@PathVariable Long loanId) {
        return service.calculateEMI(loanId);
    }

    @PutMapping("/approve/{loanId}")
    public Loan approveLoan(@PathVariable Long loanId) {
        return service.approveLoan(loanId);
    }

    @PutMapping("/reject/{loanId}")
    public Loan rejectLoan(@PathVariable Long loanId) {
        return service.rejectLoan(loanId);
    }

    @PostMapping("/start-saga/{loanId}")
    public LoanSaga startSaga(@PathVariable Long loanId) {
        return service.startLoanSaga(loanId);
    }

    @PostMapping("/disburse/{loanId}")
    public LoanDisbursement disburseLoan(@PathVariable Long loanId,@RequestBody LoanDisbursement disbursement) {
        return service.disburseLoan(loanId, disbursement);
    }

    @GetMapping("/repayments/{loanId}")
    public List<LoanRepayment> getRepayments(@PathVariable Long loanId) {
        return service.getRepaymentHistory(loanId);
    }

    @PutMapping("/npa/{loanId}/{status}")
    public Loan classifyNPA(@PathVariable Long loanId,@PathVariable NPAStatus status) {
        return service.classifyNPA(loanId, status);
    }

    @GetMapping("/saga/{loanId}")
    public LoanSaga getSagaStatus(@PathVariable Long loanId) {
        return service.getSagaStatus(loanId);
    }


}