package com.ikem.dotniptest.repository;

import com.ikem.dotniptest.model.Status;
import com.ikem.dotniptest.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatus(Status status);

    @Query(value = "SELECT * FROM transaction " +
            "WHERE (created_0n BETWEEN :startDate AND :endDate) " +
            "OR source_account_number = :accountNumber " +
            "OR status = :status", nativeQuery = true)
    List<Transaction> findTransactions(
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate,
            @Param("accountNumber")String accountNumber,
            @Param("status")String status);

    List<Transaction> findAllByDateCreated(LocalDate date);
}
