package com.infosys.finbank.loanservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.loanservice.model.CreditAssessment;

public interface CreditAssessmentRepository extends JpaRepository<CreditAssessment, Long> {

    Optional<CreditAssessment> findByLoanId(Long loanId);

}
