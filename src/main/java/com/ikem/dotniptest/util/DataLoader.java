package com.ikem.dotniptest.util;

import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.service.AccountService;
import com.ikem.dotniptest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    public void run(String... args) throws Exception {
        var account1 = CreateAccountDto.builder()
                .firstName("Ikemefuna")
                .lastName("Nwodo")
                .BVN("12345678901")
                .password("1234567890")
                .emailAddress("nwodofrank@gmail.com")
                .build();
        var account2 = CreateAccountDto.builder()
                .firstName("Ikemefuna")
                .lastName("Nwodo")
                .BVN("12345678902")
                .password("1234567890")
                .emailAddress("nwodofrank@gmail.com")
                .build();
        accountService.createAccount(account1);
        accountService.createAccount(account2);
    }
}

