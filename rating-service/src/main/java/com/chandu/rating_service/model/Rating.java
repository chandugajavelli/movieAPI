package com.chandu.rating_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@jakarta.persistence.Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    private Long id;

    private String name;

    private double avgRating;

    private int count;
}
