package com.ikem.dotniptest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummary {
    @Id
    @Column(unique = true)
    String summaryId;
    int successfulTransactions;
    int inSufficientFundsTransactions;
    int failedTransactions;
    int totalTransactions;
    LocalDate dateCreated;
}
