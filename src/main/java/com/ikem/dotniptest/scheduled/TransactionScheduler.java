package com.ikem.dotniptest.scheduled;

import com.ikem.dotniptest.model.Status;
import com.ikem.dotniptest.model.Transaction;
import com.ikem.dotniptest.model.TransactionSummary;
import com.ikem.dotniptest.repository.TransactionRepository;
import com.ikem.dotniptest.repository.TransactionSummaryRepository;
import com.ikem.dotniptest.service.TransactionSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionScheduler {

    private final TransactionSummaryRepository transactionSummaryRepository;
    private final TransactionSummaryService transactionSummaryService;
    private final TransactionRepository transactionRepository;


    @Scheduled(cron = "0 59 23 * * ?") // Runs at 11:59 PM every day
    public void calculateCommissions() {
        List<Transaction> transactions = transactionRepository.findByStatus(Status.TRANSACTION_SUCCESSFUL);

        for (Transaction transaction : transactions) {
            BigDecimal transactionFee = transaction.getTransactionFee();
            BigDecimal commission = transactionFee.multiply(new BigDecimal("0.2"));
            transaction.setCommission(commission);
            transaction.setCommissionWorthy(true);
        }
        transactionRepository.saveAll(transactions);
    }

    @Scheduled(cron = "0 59 23 * * ?") // Runs at 11:59 PM every day
    public void createTransactionSummary(){
        LocalDate date = LocalDate.now();
        List<Transaction> transactions = transactionRepository.findAllByDateCreated(date);
        TransactionSummary transactionSummary = transactionSummaryService.collateSummary(transactions);
        transactionSummaryRepository.save(transactionSummary);
    }
}
