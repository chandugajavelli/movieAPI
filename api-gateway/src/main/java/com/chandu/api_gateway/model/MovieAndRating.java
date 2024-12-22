package com.chandu.api_gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieAndRating {

    private Movie movie;
    private Rating rating;

}
