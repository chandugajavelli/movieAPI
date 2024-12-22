package com.chandu.api_gateway.model;

import java.util.ArrayList;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
  
    private Long id;
    private String director;
    private String name;
    private List<String> actors = new ArrayList<>();

    
}
