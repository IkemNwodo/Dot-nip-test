package com.ikem.dotniptest.service;

import com.ikem.dotniptest.model.Transaction;
import com.ikem.dotniptest.model.TransactionSummary;
import com.ikem.dotniptest.payload.TransactionSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionSummaryService {
    TransactionSummaryDto createSummary(LocalDate date);
    TransactionSummary collateSummary(List<Transaction> transactions);
}
