package com.besteam.bestapp.form.validators;

import com.besteam.bestapp.form.SearchDeliveryForm;
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
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indexFrom", "indexFrom.empty", "Индекс не введен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "to", "to.empty", "Введите город прибытия.");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indexTo", "indexTo.empty", "Индекс не введен");
        if(!isInteger(form.getIndexFrom())) errors.rejectValue("indexFrom", "indexFrom.notValid", "Неверный индекс.");
        if(!isInteger(form.getIndexTo())) errors.rejectValue("indexTo", "indexTo.notValid", "Неверный индекс.");
        if(!isInteger(form.getWeight())) errors.rejectValue("weight", "weight.notValid", "Поле должно содержать целое число.");
        if(!isInteger(form.getHeight())) errors.rejectValue("height", "height.notValid", "Поле высоты должно содержать целое число.");
        if(!isInteger(form.getLength())) errors.rejectValue("length", "length.notValid", "Поле длинны должно содержать целое число.");
        if(!isInteger(form.getWidth())) errors.rejectValue("width", "width.notValid", "Поле ширины должно содержать целое число.");
        //if(form.getDate().equals("дд.мм.гггг")) errors.rejectValue("date", "date.notValid", "Введите дату отправления.");

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