package com.chandu.api_gateway.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.chandu.api_gateway.model.Rating;
import com.chandu.api_gateway.model.RatingRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@Slf4j
@RequestMapping("/api/public")
public class PublicController {

     @Value("${rating-service.url}")
    private String ratingServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Object> updateRating(@RequestBody RatingRequest ratingRequest) {
        //TODO: process POST request

        try{
      Rating rating =  restTemplate.postForObject(ratingServiceUrl, ratingRequest, Rating.class);
        
      return ResponseEntity.ok(rating);
        }
         catch(HttpStatusCodeException ex)
        {   log.error("error addding movie");
            return ResponseEntity.status(ex.getStatusCode())
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(ex.getResponseBodyAsString());
        }
    }
    
    
}
