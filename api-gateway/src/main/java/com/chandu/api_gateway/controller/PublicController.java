package com.chandu.api_gateway.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.chandu.api_gateway.model.Rating;
import com.chandu.api_gateway.model.RatingRequest;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Slf4j
@RequestMapping("/api/public")
public class PublicController {

    @Value("${rating-service.url}")
    private String ratingServiceUrl;

    
    RestTemplate restTemplate = new RestTemplate();
    @PutMapping("/updateRating")
    public ResponseEntity<Object> updateRating(@RequestBody RatingRequest ratingRequest) {

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
