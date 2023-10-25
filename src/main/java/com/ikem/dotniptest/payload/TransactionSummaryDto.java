package com.ikem.dotniptest.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDto {
    String summaryId;
    int successfulTransactions;
    int inSufficientFundsTransactions;
    int failedTransactions;
    int totalTransactions;
    LocalDate dateCreated;
}
