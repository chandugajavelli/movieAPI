package com.chandu.movie_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandu.movie_service.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{
    
}
