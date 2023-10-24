package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldLengthValidator implements ConstraintValidator<FieldLengthValid,String> {

    private Logger logger= LoggerFactory.getLogger(FieldLengthValidator.class);

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 10;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        logger.info("Message from isValid: {}",value);

        if(value == null) {
            return false;
        }

        int length = value.length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }
}
