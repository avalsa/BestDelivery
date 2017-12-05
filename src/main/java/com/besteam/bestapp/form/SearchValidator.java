package com.besteam.bestapp.form;

import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;


@Component
public class SearchValidator implements Validator{

    public boolean supports(Class<?> clazz) {
        return clazz.equals(SearchDeliveryForm.class);
    }

    public void validate(Object command, Errors errors){
        SearchDeliveryForm form = (SearchDeliveryForm) command;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "from", "from.empty", "Введите город отправления.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "to", "to.empty", "Введите город прибытия.");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", "weight.empty", "Введите вес посылки.");
        if(!isInteger(form.getWeight())) errors.rejectValue("weight", "weight.notValid", "Поле должно содержать число.");
        if(form.getDate().equals("дд.мм.гггг")) errors.rejectValue("date", "date.notValid", "Введите дату отправления.");
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