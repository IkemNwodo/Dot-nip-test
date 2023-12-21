package com.ikem.dotniptest.service;

import com.ikem.dotniptest.model.Account;
import com.ikem.dotniptest.payload.DepositDto;
import com.ikem.dotniptest.payload.TransferDto;
import com.ikem.dotniptest.repository.AccountRepository;
import com.ikem.dotniptest.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

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

}
