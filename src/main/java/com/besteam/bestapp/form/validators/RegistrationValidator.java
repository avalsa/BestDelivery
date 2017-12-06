package com.besteam.bestapp.form.validators;

import com.besteam.bestapp.form.RegistrationForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

@Component
public class RegistrationValidator implements Validator{

    public boolean supports(Class<?> clazz) {
        return clazz.equals(RegistrationForm.class);
    }

    public void validate(Object command, Errors errors){
        RegistrationForm form = (RegistrationForm) command;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty", "Введите логин.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Введите пароль.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty", "Введите имя.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.empty", "Введите фамилию.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordAgain", "passwordAgain.empty", "Подтвердите пароль.");
        if(! form.getPasswordAgain().equals(form.getPassword())) errors.rejectValue("passwordAgain", "passwordAgain.notValid", "Пароль не подтвержден");

        if( !EmailValidator.getInstance().isValid( form.getEmail() ) ){
            errors.rejectValue("email", "email.notValid", "Неправильно указан электронный адрес");
        }
    }

    private static boolean isInteger(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
