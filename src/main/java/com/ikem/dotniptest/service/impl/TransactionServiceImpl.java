package com.ikem.dotniptest.service.impl;

import com.ikem.dotniptest.exception.InsufficientBalanceException;
import com.ikem.dotniptest.exception.TransactionException;
import com.ikem.dotniptest.exception.TransferToSelfException;
import com.ikem.dotniptest.model.Account;
import com.ikem.dotniptest.model.Status;
import com.ikem.dotniptest.model.Transaction;
import com.ikem.dotniptest.payload.DepositDto;
import com.ikem.dotniptest.payload.TransactionDto;
import com.ikem.dotniptest.payload.TransferDto;
import com.ikem.dotniptest.repository.AccountRepository;
import com.ikem.dotniptest.repository.TransactionRepository;
import com.ikem.dotniptest.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final ModelMapper mapper;

    @Override
    public void processTransaction(TransferDto transferDto) {
        var uuid = UUID.randomUUID();

        var transaction = Transaction.builder()
                .transactionReference(uuid)
                .amount(transferDto.getAmount())
                .sourceAccountNumber(transferDto.getSourceAccountNumber())
                //.dateCreated(LocalDateTime.now())
                .destinationAccountNumber(transferDto.getDestinationAccountNumber())
                .build();
        log.debug("{} Transaction initiated", transaction);
        try {
            if (transferDto.getSourceAccountNumber().equals(transferDto.getDestinationAccountNumber())) {
                transaction.setStatus(Status.TRANSACTION_FAILED);
                transactionRepository.save(transaction);
                log.debug("{} Transaction failed", transaction);
                throw new TransferToSelfException(transaction.getDestinationAccountNumber());
            }
            var updatedTransaction = withdraw(transaction);
            transactionRepository.save(updatedTransaction);
            log.debug("{} Transaction saved", updatedTransaction);
        } catch (Exception e) {
            transaction.setDescription(e.getMessage());
            transaction.setStatus(Status.TRANSACTION_FAILED);
            transactionRepository.save(transaction);
            log.error("Error: {}", Status.TRANSACTION_FAILED);
            throw new TransactionException(Status.TRANSACTION_FAILED + ": " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<TransactionDto> getAllTransactions(String status, String accountNumber, LocalDate startDate, LocalDate endDate) {
        log.info("Getting transactions with query parameters: {}, {}, {}, {}", status, accountNumber, startDate, endDate);

        List<TransactionDto> transactionDtos = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findTransactions(startDate, endDate, accountNumber, status);
        if (transactions.size() > 0){
            transactionDtos = transactions.stream().map(
                            transaction1 -> mapper.map(transaction1, TransactionDto.class))
                    .collect(Collectors.toList());
            log.info("Successfully fetched transactions with query parameters: {}, {}, {}, {}", status, accountNumber, startDate, endDate);
        }

        return transactionDtos;
    }

    @Override
    public DepositDto deposit(DepositDto depositDto) {
        var account = fetchAccount(depositDto.getAccountNumber());

        account.setBalance(account.getBalance().add(depositDto.getAmount()));
        var updatedAccount = accountRepository.save(account);
        return DepositDto.builder()
                .accountNumber(updatedAccount.getAccountNumber())
                .amount(depositDto.getAmount())
                .currentBalance(updatedAccount.getBalance())
                .message("Deposit successful")
                .build();
    }
    private Transaction withdraw(Transaction transaction) {
        var sourceAccount = fetchAccount(transaction.getSourceAccountNumber());
        var destinationAccount = fetchAccount(transaction.getDestinationAccountNumber());

        if (sourceAccount != null) {
            var transactionFee = calculateTransactionFee(transaction.amount);
            var billedAmount = transaction.getAmount().add(transactionFee);
            var balance = sourceAccount.getBalance().subtract(billedAmount);

            transaction.setTransactionFee(transactionFee);
            transaction.setBilledAmount(billedAmount);

            if (balance.doubleValue() < 0.0) {
                transaction.setStatus(Status.INSUFFICIENT_FUND);
                transaction.setDescription("Balance is insufficient for the transaction.");
                throw new InsufficientBalanceException(sourceAccount.getAccountName(), billedAmount, sourceAccount.getBalance());
            }
            sourceAccount.setBalance(balance);
            accountRepository.save(sourceAccount);

            if (destinationAccount != null) {
                destinationAccount.setBalance(destinationAccount.getBalance().add(transaction.getAmount()));
                accountRepository.save(destinationAccount);
            } else {
                transaction.setStatus(Status.TRANSACTION_FAILED);
                transaction.setDescription("Destination account not found!");
            }
        } else {
            transaction.setStatus(Status.TRANSACTION_FAILED);
            transaction.setDescription("Source account not found!");
        }
        return transaction;
    }

    private Account fetchAccount(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber).orElse(null);
    }

    private BigDecimal calculateTransactionFee(BigDecimal amount) {
        log.info("Calculating transaction fee...");
        final BigDecimal TRANSACTION_FEE_CAP = BigDecimal.valueOf(100.0);
        final double TRANSACTION_FEE_PERCENTAGE = 0.005;
        BigDecimal transactionFee = amount.multiply(BigDecimal.valueOf(TRANSACTION_FEE_PERCENTAGE));

        if (transactionFee.compareTo(TRANSACTION_FEE_CAP) >= 0) {
            return TRANSACTION_FEE_CAP;
        } else
            return transactionFee;
    }
}
