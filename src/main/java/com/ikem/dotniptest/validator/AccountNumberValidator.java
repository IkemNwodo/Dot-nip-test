package com.ikem.dotniptest.validator;

import com.ikem.dotniptest.annotation.AccountNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountNumberValidator implements ConstraintValidator<AccountNumber, String> {
    @Override
    public void initialize(AccountNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext constraintValidatorContext) {
        return accountNumber != null && accountNumber.matches("[0-9]+") && accountNumber.length() == 10;
    }
}
