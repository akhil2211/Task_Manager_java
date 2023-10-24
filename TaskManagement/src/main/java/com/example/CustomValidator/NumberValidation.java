package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberValidation implements ConstraintValidator<Numeric,String> {

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if(fieldValue==null){
            return false;}

        try {
            Double.parseDouble(fieldValue);
            return true;}
        catch (NumberFormatException e) {
            return false; }

    }

}
