package com.ikem.dotniptest.repository;

import com.ikem.dotniptest.model.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary, Long> {
}
