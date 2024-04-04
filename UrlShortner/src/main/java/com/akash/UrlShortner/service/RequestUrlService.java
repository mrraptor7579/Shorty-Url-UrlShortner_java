package com.akash.UrlShortner.service;


import com.akash.UrlShortner.model.RequestUrl;
import com.akash.UrlShortner.repository.RequestUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class RequestUrlService {

    @Autowired
    private RequestUrlRepository requestUrlRepository;

    // Method to generate short alias for a given URL and save it to the database
    public String shortenURL(String originalURL) {
        // Generate short alias (e.g., using hashing algorithms or encoding)
        String shortUrl = generateShortAlias(originalURL);

        // Save URL mapping to the database
        RequestUrl requestUrl = new RequestUrl();
        requestUrl.setShortUrl(shortUrl);
        requestUrl.setOriginalURL(originalURL);
        requestUrlRepository.save(requestUrl);

        return shortUrl;
    }

    // Method to retrieve original URL from short alias
    public String getOriginalURL(String shortUrl) {
        Optional<RequestUrl> optionalMapping = requestUrlRepository.findByShortUrl(shortUrl);
        return optionalMapping.map(RequestUrl::getOriginalURL).orElse(null);
    }

    // Method to generate short alias (dummy implementation)
    private String generateShortAlias(String originalURL) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalURL.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8); // Use first 8 characters as short alias
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Handle error if hashing algorithm is not available
            return null;
        }
    }

    public String checkDuplicate(String originalURL) {
       String shortUrl= requestUrlRepository.findByOriginalUrl(originalURL);
       return shortUrl;
    }
}

