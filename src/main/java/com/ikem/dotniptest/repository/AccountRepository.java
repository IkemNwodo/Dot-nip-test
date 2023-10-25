package com.ikem.dotniptest.repository;

import com.ikem.dotniptest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(nativeQuery = true, value = "SELECT IFNULL(MAX(account_number), '0') FROM account")
    String findLastAccountNumber();

    Optional<Account> findAccountByAccountNumber(String accountNumber);

}