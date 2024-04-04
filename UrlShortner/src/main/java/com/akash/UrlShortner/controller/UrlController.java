package com.akash.UrlShortner.controller;

import com.akash.UrlShortner.model.RequestUrl;
import com.akash.UrlShortner.service.RequestUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Controller
public class UrlController {

    @Autowired
    private RequestUrlService requestUrlService;

    // POST endpoint for shortening a URL
    @PostMapping("/shorten")
    public String shortenURL(@ModelAttribute("requestUrl") RequestUrl requestUrl, Model model) {
        // Shorten the original URL and add it to the model
        String shtUrl = requestUrlService.checkDuplicate(requestUrl.getOriginalURL());
        if (shtUrl != null) {
           model.addAttribute("shortUrl",shtUrl);
        }
        else {
            String shortUrl = requestUrlService.shortenURL(requestUrl.getOriginalURL());
            model.addAttribute("shortUrl", shortUrl);
        }
        model.addAttribute("origialURL", requestUrl.getOriginalURL());

        return "index";
    }

    // GET endpoint for redirecting to the original URL based on the short URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalURL(@PathVariable String shortUrl) {
        // Retrieve the original URL corresponding to the short URL
        String originalURL = requestUrlService.getOriginalURL(shortUrl);
        if (originalURL != null) {
            // If original URL found, redirect to it
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalURL)).build();
        } else {
            // If original URL not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    // GET endpoint for displaying the main page
    @GetMapping("/index")
    public String showMain(Model model) {
        // Create a new RequestUrl object and add it to the model
        RequestUrl requestUrl = new RequestUrl();
        model.addAttribute("requestUrl", requestUrl);
        return "index";
    }
}