package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringValidate implements ConstraintValidator<StringValid,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value==null || value.isBlank()){
            return false;}

        return value.matches("^[a-zA-Z]*$");
    }
}
