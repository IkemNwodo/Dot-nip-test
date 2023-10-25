package com.ikem.dotniptest.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    @Override
    public String getMessage() {
        return "You have an account with us already";
    }
}
