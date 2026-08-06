package com.infosys.finbank.loanservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.loanservice.model.LoanSaga;

public interface LoanSagaRepository extends JpaRepository<LoanSaga, Long> {

    Optional<LoanSaga> findByLoanId(Long loanId);

}
