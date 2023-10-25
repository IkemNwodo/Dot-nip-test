package com.ikem.dotniptest.service;

import com.ikem.dotniptest.model.Status;
import com.ikem.dotniptest.model.Transaction;
import com.ikem.dotniptest.payload.DepositDto;
import com.ikem.dotniptest.payload.TransactionDto;
import com.ikem.dotniptest.payload.TransferDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    void processTransaction(TransferDto transaction);
    DepositDto deposit(DepositDto depositDto);
    List<TransactionDto> getAllTransactions(String status, String userId, LocalDate startDate, LocalDate endDate);

}
