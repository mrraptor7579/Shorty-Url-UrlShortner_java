package com.akash.UrlShortner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError() {
        // Handle errors here, e.g., redirect to a custom error page
        return "errorPage";
    }
}