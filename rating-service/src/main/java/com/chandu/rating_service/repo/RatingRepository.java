package com.chandu.rating_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandu.rating_service.model.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
     Rating findByName(String name);

     List<Rating>  findAllByAvgRatingBetween(double min, double max);
}
