package com.kars.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kars.test.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {}
