package com.besteam.bestapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @RequestMapping("review")
    public String getDeliveryList(@RequestParam(value="review", required=false, defaultValue="Rew") String review, Model model) {
        model.addAttribute("review", review);
        return "review";
    }

}
