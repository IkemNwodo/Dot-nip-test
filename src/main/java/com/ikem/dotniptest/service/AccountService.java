package com.ikem.dotniptest.service;

import com.ikem.dotniptest.payload.AccountDto;
import com.ikem.dotniptest.payload.CreateAccountDto;

public interface AccountService {
    AccountDto createAccount(CreateAccountDto createAccountDto);
}
