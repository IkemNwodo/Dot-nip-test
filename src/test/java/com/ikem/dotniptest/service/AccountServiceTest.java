package com.ikem.dotniptest.service;

import com.ikem.dotniptest.model.User;
import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.repository.AccountRepository;
import com.ikem.dotniptest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        var account = CreateAccountDto.builder()
                .firstName("Ikemefuna")
                .lastName("Nwodo")
                .BVN("12345678901")
                .password("1234567890")
                .emailAddress("nwodofrank@gmail.com")
                .build();

        var bvn = Long.parseLong(account.getBVN());

        var user = User.builder()
                .bvn(bvn)
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .password(account.getPassword())
                .emailAddress(account.getEmailAddress())
                .build();
        entityManager.persist(user);
        entityManager.flush();
    }
    @Test
    public void createAccount() {
        var account = CreateAccountDto.builder()
                .firstName("Ikemefuna")
                .lastName("Nwodo")
                .BVN("12345678901")
                .password("1234567890")
                .emailAddress("nwodofrank@gmail.com")
                .build();

        accountService.createAccount(account);
        assertTrue(accountRepository.findAccountByAccountNumber("0000000001").isPresent());
    }
}
