package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
source of code
https://stackoverflow.com/questions/25356781/spring-boot-remove-whitelabel-error-page

 */
@Controller
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(Model model) {
        model.addAttribute("Error", "an error occurred");
        return "result";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}