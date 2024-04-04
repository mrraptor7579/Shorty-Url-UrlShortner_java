package com.akash.UrlShortner.repository;

import com.akash.UrlShortner.model.RequestUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RequestUrlRepository extends JpaRepository<RequestUrl,Long> {
    Optional<RequestUrl> findByShortUrl(String shortUrl);

    @Query("select s.shortUrl from RequestUrl s where s.originalURL=:originalURL")
    String findByOriginalUrl(String originalURL);
}
