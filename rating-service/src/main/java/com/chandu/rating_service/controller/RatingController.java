package com.chandu.rating_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chandu.rating_service.model.Rating;
import com.chandu.rating_service.model.RatingRequest;
import com.chandu.rating_service.service.RatingService;



@RestController
@Slf4j
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;


    @GetMapping("/{name}")
    public ResponseEntity<Rating> getRating(@PathVariable String name){
        Rating rating = ratingService.fetchRating(name);
        log.info("Returning rating for movie: {}", name);
        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<Rating> updateRating(@RequestBody RatingRequest request){
        Rating rating = ratingService.updateAverage(request.getName(), request.getStars());
        log.info("returning new average for movie: {}", request.getName());
        return ResponseEntity.ok(rating);
    }
}
