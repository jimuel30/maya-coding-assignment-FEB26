package com.aparzero.maya.service;


import com.aparzero.maya.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Recover;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonPlaceholderService {
    private final RestTemplate restTemplate;

    @Value("${third-party.json-place-holder.url.users}")
    private String usersUrl;


    @Retryable(
            retryFor = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public List<User> getUsers(){
        User[] users = callJsonPlaceHolderApi(
                usersUrl,
                User[].class
        );
        return Arrays.asList(users);
    }

    private <T> T callJsonPlaceHolderApi(String url, Class<T> responseClass) {
        log.info("CALLING URL: {}",url);
        log.info("RESPONSE CLASS: {}",responseClass);

        ResponseEntity<T> response =
                restTemplate.getForEntity(url, responseClass);

        log.info("RESPONSE: {}",response);
        return response.getBody();
    }


    @Recover
    public List<User> handleFailure(Exception e) {
        log.error("--- RECOVERY TRIGGERED: {} ---", e.getMessage());
        return Collections.emptyList();
    }


}