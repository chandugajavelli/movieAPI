package com.chandu.api_gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.chandu.api_gateway.model.Movie;
import com.chandu.api_gateway.model.MovieAndRating;
import com.chandu.api_gateway.model.Rating;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

    public  RestTemplate restTemplate = new RestTemplate();

    @Value("${movie-service.url}")
    private String movieServiceUrl;
    @Value("${rating-service.url}")
    private String ratingServiceUrl;

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        try{
        log.info("adding movie");
        Movie savedMovie = restTemplate.postForObject(movieServiceUrl, movie, Movie.class);

        return ResponseEntity.ok(savedMovie);
        }
        catch(HttpStatusCodeException ex)
        {   log.error("error addding movie");
            return ResponseEntity.status(ex.getStatusCode())
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(ex.getResponseBodyAsString());
        }


    }

    @PutMapping("/add/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Movie movie){
        try{
       restTemplate.put(movieServiceUrl+"/"+id, movie);
        return ResponseEntity.ok().build();
        }
        catch(HttpStatusCodeException ex)
        {   log.error("error updatingng movie");
            return ResponseEntity.status(ex.getStatusCode())
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(ex.getResponseBodyAsString());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getMovieAndRating(@PathVariable Long id) {
        Movie movie;
       try{  movie = restTemplate.getForObject(movieServiceUrl+"/"+id, Movie.class);
    }
    catch(HttpStatusCodeException ex){
        log.error("error updatingng movie");
            return ResponseEntity.status(ex.getStatusCode())
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(ex.getResponseBodyAsString());
    }



        Rating rating;

        try{
        if (movie != null) {
            rating = restTemplate.getForObject(ratingServiceUrl+"/"+movie.getName(),Rating.class);
        } else {
            rating = new Rating(null, null, -1, -1); // handle null movie case
        }
        }
        catch(HttpStatusCodeException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                rating = new Rating(null,movie != null ? movie.getName() : null,0.0,0);
            }
            else  
                rating = new Rating(null,movie != null ? movie.getName() : null,-1,-1);  //if rating service is down
        }


        MovieAndRating movieAndRating = new MovieAndRating(movie,rating);

        return ResponseEntity.ok(movieAndRating);
    }
    
    
}
