package com.chandu.api_gateway.model;

import lombok.Data;

@Data
public class RatingRequest {
    private String name;
    private double stars;
}
