package com.chandu.movie_service.api;

import java.util.Optional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chandu.movie_service.exceptions.NotFoundException;
import com.chandu.movie_service.model.Movie;
import com.chandu.movie_service.service.MovieService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieController {


    @Autowired
    MovieService movieService;


    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id)
    {
        Movie movie = movieService.read(id)
                                        .orElseThrow(() -> new NotFoundException("Movie not found id:"+id));

        log.info("returned movie with id {}",id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getMovie()
    {
        List<Movie> movies = movieService.getAllMovies();

       
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/allwithSort") //sorting 
    public ResponseEntity<List<Movie>> getMovie(@RequestParam String field)
    {
        List<Movie> movies = movieService.getAllMoviesWithSort(field);

       
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/allwithPaging") //paging
    public ResponseEntity<Page<Movie>> getMovie(@RequestParam int pageNo, @RequestParam int pageSize)
    {
        Page<Movie> movies = movieService.getAllMoviesWithPaging(pageNo, pageSize);

       
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/allwithPagingAndSort") //paging and sorting
    public ResponseEntity<Page<Movie>> getMovie(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String field)
    {
        Page<Movie> movies = movieService.getAllMoviesWithPagingAndSort(pageNo, pageSize, field);

       
        return ResponseEntity.ok(movies);
    }



    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie)
    {
        Movie  Createdmovie = movieService.create(movie);
        log.info("created movie with id {}",Createdmovie.getId());
        return ResponseEntity.ok(Createdmovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.update(id, movie);
        log.info("updated movie with id {}",id);
        return ResponseEntity.ok(updatedMovie); // HTTP 200 OK with updated resource
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id)
    {
        log.info("deleted movie with id {}",id);
        movieService.delete(id);
    }

    @PostMapping("/addAll")
    public ResponseEntity<String> addMovies(@RequestBody List<Movie> movies) {
        try {
            movieService.createMany(movies);
            return ResponseEntity.ok("Movies saved successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

}
