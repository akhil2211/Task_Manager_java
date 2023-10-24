package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<EmailValid,String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return false;
        }
        return email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}");
    }
}
