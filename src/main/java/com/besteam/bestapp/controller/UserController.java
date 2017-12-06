package com.besteam.bestapp.controller;

import com.besteam.bestapp.form.*;
import com.besteam.bestapp.form.validators.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
@Controller

public class UserController {

    @Autowired
    private RegistrationValidator registryValidator;

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String doRegistration(@ModelAttribute("registrationForm") @Valid RegistrationForm form, Model model, BindingResult bindingResult) {
        registryValidator.validate(form,bindingResult);
        if (bindingResult.hasErrors())return "registration";
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("registration", "registrationForm", new RegistrationForm());
    }


    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public String doAuth(@ModelAttribute("authForm") @Valid AuthForm form, Model model, BindingResult bindingResult) {
        model.addAttribute("authForm", new AuthForm());
        return "auth";
    }

    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public ModelAndView showAuthForm() {
        return new ModelAndView("auth", "authForm", new AuthForm());
    }
}
