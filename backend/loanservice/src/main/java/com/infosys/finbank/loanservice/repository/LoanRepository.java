package com.infosys.finbank.loanservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.loanservice.model.Loan;
import java.util.List;
import java.util.UUID;
import com.infosys.finbank.loanservice.enums.LoanStatus;
import com.infosys.finbank.loanservice.enums.LoanType;
import com.infosys.finbank.loanservice.enums.NPAStatus;






public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByLoanNumber(String loanNumber);

    List<Loan> findByCustomerId(UUID customerId);

    List<Loan> findByLoanStatus(LoanStatus status);

    List<Loan> findByLoanType(LoanType type);

    List<Loan> findByAutoDebitEnabled(Boolean enabled);

    List<Loan> findByNpaStatus(NPAStatus status);

}
