package com.ikem.dotniptest.service.impl;

import com.ikem.dotniptest.model.Status;
import com.ikem.dotniptest.model.Transaction;
import com.ikem.dotniptest.model.TransactionSummary;
import com.ikem.dotniptest.payload.TransactionSummaryDto;
import com.ikem.dotniptest.repository.TransactionRepository;
import com.ikem.dotniptest.repository.TransactionSummaryRepository;
import com.ikem.dotniptest.service.TransactionSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

    private final ModelMapper mapper;
    private final TransactionRepository transactionRepository;
    private final TransactionSummaryRepository transactionSummaryRepository;

    @Override
    public TransactionSummaryDto createSummary(LocalDate date) {
        log.info("Getting all transactions for {}", date);
        List<Transaction> transactions = transactionRepository.findAllByDateCreated(date);

        TransactionSummary entity = collateSummary(transactions);

        transactionSummaryRepository.save(entity);

        log.info("Transactions summary created and saved");
        return mapper.map(entity, TransactionSummaryDto.class);
    }

    public TransactionSummary collateSummary(List<Transaction> transactions){
        TransactionSummary result = new TransactionSummary();
        var ref = new Object() {
            int successfulTransactions = 0;
            int inSufficientFundsTransactions = 0;
            int failedTransactions = 0;
        };

        if (transactions != null){
            transactions.forEach(transaction -> {
                Status status = transaction.getStatus();
                if (status.equals(Status.TRANSACTION_SUCCESSFUL)){
                    ref.successfulTransactions++;
                } else if (status.equals(Status.INSUFFICIENT_FUND)){
                    ref.inSufficientFundsTransactions++;
                } else
                    ref.failedTransactions++;
            });
            String uuid = UUID.randomUUID().toString();
            result.setSummaryId(uuid);
            result.setSuccessfulTransactions(ref.successfulTransactions);
            result.setInSufficientFundsTransactions(ref.inSufficientFundsTransactions);
            result.setFailedTransactions(ref.failedTransactions);
            result.setTotalTransactions(transactions.size());
            result.setDateCreated(LocalDate.now());

        }
        return result;
    }
}
