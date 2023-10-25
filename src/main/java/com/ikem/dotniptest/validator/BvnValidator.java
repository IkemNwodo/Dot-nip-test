package com.ikem.dotniptest.validator;

import com.ikem.dotniptest.annotation.BVN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BvnValidator implements ConstraintValidator<BVN, String> {
    @Override
    public void initialize(BVN constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bvn, ConstraintValidatorContext constraintValidatorContext) {
        return bvn != null && bvn.matches("[0-9]+") && bvn.length() == 11;
    }
}
