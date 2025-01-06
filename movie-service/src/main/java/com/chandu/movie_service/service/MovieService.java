package com.chandu.movie_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chandu.movie_service.exceptions.InvalidDataException;
import com.chandu.movie_service.exceptions.NotFoundException;
import com.chandu.movie_service.model.Movie;
import com.chandu.movie_service.repo.MovieRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovieService {

    @Autowired
    MovieRepository movierepo;

    public Movie create(Movie movie)
    {   
        if(movie == null)
            throw new InvalidDataException("movie is invalid: null");
       return movierepo.save(movie);
    }

    public List<Movie> getAllMovies()
    {
        return movierepo.findAll();
    }

    public List<Movie> getAllMoviesWithSort(String field)//sorting
    {
        return movierepo.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    //paging 
    public Page<Movie> getAllMoviesWithPaging(int pageNo, int pageSize)//sorting
    {
        Page<Movie> movies =  movierepo.findAll(PageRequest.of(pageNo, pageSize));
        return movies;
    }




    public Optional<Movie> read(Long id)
    {
        return movierepo.findById(id);
    }

    public Movie update(Long id, Movie update)
    {
        if(update == null || id == null)
            throw new NotFoundException("movie does not exist to update, id:"+id);

        if(movierepo.existsById(id))
        {
            Movie movie = movierepo.getReferenceById(id);
            movie.setName(update.getName());
            movie.setDirector(update.getDirector());
            movie.setActors(update.getActors());

            movierepo.save(movie);
            return movie;
        }
        
        else    
            throw new NotFoundException("movie does not exist to update, id:"+id);
    
    }

    public void delete(Long id)
    {
        if(movierepo.existsById(id))
            movierepo.deleteById(id);
        else    
            throw new NotFoundException("movie does not exist to delete, id:"+id);
    }

    public void createMany(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            throw new IllegalArgumentException("The movies list cannot be null or empty");
        }
        movierepo.saveAll(movies);
    }

    public Page<Movie> getAllMoviesWithPagingAndSort(int pageNo, int pageSize, String field) {
        // TODO Auto-generated method stub
       Page<Movie> movies =  movierepo.findAll(PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, field)));
       return movies;
    }



    
}
