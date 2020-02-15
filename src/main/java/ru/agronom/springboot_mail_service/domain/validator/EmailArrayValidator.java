package ru.agronom.springboot_mail_service.domain.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailArrayValidator implements ConstraintValidator<EmailArray, String[]> {

    @Override
    public void initialize(EmailArray constraintAnnotation) {

    }

    @Override
    public boolean isValid(String[] strings, ConstraintValidatorContext constraintValidatorContext) {
        if (strings == null) {
            return false;
        }
        EmailValidator validator = new EmailValidator();
        for (String s: strings){
            if(!validator.isValid(s,constraintValidatorContext)){
                return false;
            }
        }
        return true;

    }
}
