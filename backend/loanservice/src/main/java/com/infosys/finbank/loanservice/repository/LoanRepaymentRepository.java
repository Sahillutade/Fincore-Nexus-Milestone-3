package com.infosys.finbank.loanservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.finbank.loanservice.enums.PaymentStatus;
import com.infosys.finbank.loanservice.model.LoanRepayment;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Long> {

    List<LoanRepayment> findByLoanIdOrderByInstallmentNumber(Long loanId);

    List<LoanRepayment> findByPaymentStatus(PaymentStatus status);

    List<LoanRepayment> findByDueDateBeforeAndPaymentStatus(LocalDate date, PaymentStatus status);

}
