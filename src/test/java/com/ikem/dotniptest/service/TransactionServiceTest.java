package com.ikem.dotniptest.service;

import com.ikem.dotniptest.exception.InsufficientBalanceException;
import com.ikem.dotniptest.exception.TransactionException;
import com.ikem.dotniptest.model.Account;
import com.ikem.dotniptest.model.Transaction;
import com.ikem.dotniptest.model.User;
import com.ikem.dotniptest.payload.AccountDto;
import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.payload.DepositDto;
import com.ikem.dotniptest.payload.TransferDto;
import com.ikem.dotniptest.repository.AccountRepository;
import com.ikem.dotniptest.repository.TransactionRepository;
import com.ikem.dotniptest.service.impl.TransactionServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModelMapper mapper;

    /*@BeforeEach
    public void setUp() {

        var accountDto = AccountDto.builder()
                .accountNumber("0000000001")
                .balance(BigDecimal.valueOf(100000.00))
                .accountName("Ikemefuna Nwodo")
                .build();

        entityManager.persist(mapper.map(accountDto, Account.class));
        entityManager.flush();
    }*/
    @Test
    public void test_deposit() {

        var depositDto = DepositDto.builder()
                .accountNumber("0000000001")
                .amount(BigDecimal.valueOf(100000.00))
                .message("Self deposit")
                .build();

        transactionService.deposit(depositDto);
        assertThat(transactionService.deposit(depositDto)).isInstanceOf(DepositDto.class);
    }
    @Test
    public void testProcessTransaction_Successful() {

        var transfer = TransferDto.builder()
                .amount(BigDecimal.valueOf(10000.00))
                .sourceAccountNumber("0000000001")
                .destinationAccountNumber("0000000002")
                .build();

        transactionService.processTransaction(transfer);

        // Mock necessary behavior for fetchAccount and withdraw methods
        when(accountRepository.findAccountByAccountNumber(anyString())).thenReturn(Optional.of(mock(Account.class)));

        // Perform the test
        // Add assertions as needed
    }


    /*@Test
    public void testProcessTransaction_InsufficientBalance() {
        // Set up test data

        var transferDto = TransferDto.builder().build();
        // Set up transferDto fields

        // Mock necessary behavior for fetchAccount and withdraw methods
        var mockedAccount = mock(Account.class);
        when(accountRepository.findAccountByAccountNumber(anyString())).thenReturn(Optional.of(mockedAccount));
        when(transactionRepository.save(any(Transaction.class))).thenThrow(InsufficientBalanceException.class);

        // Perform the test and assert
        assertThrows(TransactionException.class, () -> transactionService.processTransaction(transferDto));
    }*/

    // More tests for different scenarios in TransactionService can be added similarly
}
