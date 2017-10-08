package com.besteam.bestapp.controller;

import com.besteam.bestapp.form.SearchDeliveryForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class SearchDeliveryController {

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchDelivery(@ModelAttribute("searchDeliveryForm") SearchDeliveryForm form, Model model) {
        String s = form.getFrom();
        model.addAttribute("name", form.getFrom());
        return "index";

    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("search", "searchDeliveryForm", new SearchDeliveryForm());
    }

}
