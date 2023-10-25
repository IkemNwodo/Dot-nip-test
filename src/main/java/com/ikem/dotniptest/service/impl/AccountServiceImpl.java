package com.ikem.dotniptest.service.impl;

import com.ikem.dotniptest.exception.AccountAlreadyExistsException;
import com.ikem.dotniptest.model.Account;
import com.ikem.dotniptest.model.User;
import com.ikem.dotniptest.payload.AccountDto;
import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.repository.AccountRepository;
import com.ikem.dotniptest.repository.UserRepository;
import com.ikem.dotniptest.service.AccountService;
import com.ikem.dotniptest.util.Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    @Override
    public AccountDto createAccount(CreateAccountDto createAccountDto) {
        var bvn = Long.parseLong(createAccountDto.getBVN());
        if (userRepository.existsByBvn(bvn))
            throw new AccountAlreadyExistsException();

        var user = User.builder()
                .emailAddress(createAccountDto.getEmailAddress())
                .firstName(createAccountDto.getFirstName())
                .lastName(createAccountDto.getLastName())
                .bvn(bvn)
                .password(createAccountDto.getPassword())
                .build();
        userRepository.save(user);

        var account = Account.builder()
                .accountNumber(Generator.accountNumber(accountRepository.findLastAccountNumber()))
                .accountName(createAccountDto.getFirstName().concat(createAccountDto.getLastName()))
                .balance(BigDecimal.valueOf(0.0))
                .transactions(Set.of())
                .build();
        var accountCreated = accountRepository.save(account);
        log.debug("{} is the account info", account);

        return mapToDto(accountCreated);
    }

    private AccountDto mapToDto(Account accountCreated) {
        return AccountDto.builder()
                .accountName(accountCreated.getAccountName())
                .accountNumber(accountCreated.getAccountNumber())
                .balance(accountCreated.getBalance())
                .transactions(accountCreated.getTransactions())
                .build();
    }
}
