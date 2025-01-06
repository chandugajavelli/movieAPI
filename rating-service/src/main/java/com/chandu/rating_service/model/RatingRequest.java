package com.chandu.rating_service.model;

import lombok.Data;

@Data
public class RatingRequest {
    private String name;
    private double stars;
}
