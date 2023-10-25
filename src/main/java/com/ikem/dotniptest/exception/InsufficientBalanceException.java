package com.ikem.dotniptest.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String accountName, BigDecimal withdrawalAmount, BigDecimal currentBalance) {
        super(String.format("%s has insufficient balance for this %s transaction. Current balance is %s", accountName, withdrawalAmount, currentBalance));
    }
}
