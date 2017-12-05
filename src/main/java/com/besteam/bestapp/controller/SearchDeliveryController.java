package com.besteam.bestapp.controller;

import com.besteam.bestapp.form.SearchDeliveryForm;

import com.besteam.bestapp.form.SearchResultForm;
import com.besteam.bestapp.form.SearchValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@ControllerAdvice
public class SearchDeliveryController {
    @Autowired
    private SearchValidator searchFormValidator;

    //методы для обработки ошибок

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchDelivery(@ModelAttribute("searchDeliveryForm") @Valid SearchDeliveryForm form, Model model, BindingResult bindingResult) {
        searchFormValidator.validate(form,bindingResult);
        if (bindingResult.hasErrors())return "search";
        String s = form.getFrom();
        model.addAttribute("name", form.getFrom());
        model.addAttribute("searchResultForm", new SearchResultForm());
        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("search", "searchDeliveryForm", new SearchDeliveryForm());
    }

    public boolean isFrom(String from){
        return !from.isEmpty();
    }

    public boolean isTo(String to){
        return !to.isEmpty();
    }

    public boolean isWeight(String weight){
        return !weight.isEmpty();
    }


}
