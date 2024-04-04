package com.akash.UrlShortner.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "short_urls")
public class RequestUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortUrl;

    @Column(unique = true)
    private String originalURL;

}