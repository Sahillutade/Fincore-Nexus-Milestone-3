package com.infosys.finbank.loanservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.loanservice.model.LoanDisbursement;

public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Long> {

    Optional<LoanDisbursement> findByLoanId(Long loanId);

}
