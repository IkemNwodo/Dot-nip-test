package com.ikem.dotniptest.annotation;

import com.ikem.dotniptest.validator.BvnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BvnValidator.class)
@Target( {ElementType.FIELD, ElementType.METHOD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface BVN {
    String message() default "BVN should be 11 digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
