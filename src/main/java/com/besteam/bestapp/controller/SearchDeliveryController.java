package com.besteam.bestapp.controller;

import com.besteam.bestapp.form.SearchDeliveryForm;

import com.besteam.bestapp.form.SearchResultForm;
import com.besteam.bestapp.form.validators.SearchValidator;
import com.besteam.bestapp.service.search.SearchDeliveryResult;
import com.besteam.bestapp.service.search.SearchDeliveryResults;
import com.besteam.bestapp.service.search.SearchDeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.naming.directory.SearchResult;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@ControllerAdvice
public class SearchDeliveryController {
    @Autowired
    private SearchValidator searchFormValidator;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchDelivery(@ModelAttribute("searchDeliveryForm") @Valid SearchDeliveryForm form, Model model, BindingResult bindingResult) {
        searchFormValidator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) return "search";
        SearchDeliveryService searchDeliveryService = new SearchDeliveryService();
        SearchDeliveryResults searchDeliveryResults = searchDeliveryService.doRequest(form);
        if (searchDeliveryResults.getResults().isEmpty()) return "search";
        SearchDeliveryResult r = getBestResult(searchDeliveryResults, form);
        model.addAttribute("name", form.getFrom());
        model.addAttribute("searchResultForm", new SearchResultForm(r.getDelivery(), r.getCost(), r.getDeliveryTime()));
        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("search", "searchDeliveryForm", new SearchDeliveryForm());
    }

    private static SearchDeliveryResult getBestResult(SearchDeliveryResults searchDeliveryResults, SearchDeliveryForm form) {
        SearchDeliveryResult bestResult = searchDeliveryResults.getSearchResultById(0);
        if (form.getFilter()) {
            //цена
            for (SearchDeliveryResult searchDeliveryResult : searchDeliveryResults.getResults()) {
                if (searchDeliveryResult.getCost() < bestResult.getCost()) {
                    bestResult = searchDeliveryResult;
                }
            }
        } else {
            //время
            for (SearchDeliveryResult searchDeliveryResult : searchDeliveryResults.getResults()) {
                if (searchDeliveryResult.getDeliveryTime().compareTo(bestResult.getDeliveryTime()) < 0) {
                    bestResult = searchDeliveryResult;
                }
            }
        }
        return bestResult;
    }
}
