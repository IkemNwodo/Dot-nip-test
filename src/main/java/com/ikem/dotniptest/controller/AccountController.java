package com.ikem.dotniptest.controller;

import com.ikem.dotniptest.payload.AccountDto;
import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController  {
    private final AccountService accountService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@Validated @RequestBody CreateAccountDto createAccountDto) {
        return accountService.createAccount(createAccountDto);
    }
}
