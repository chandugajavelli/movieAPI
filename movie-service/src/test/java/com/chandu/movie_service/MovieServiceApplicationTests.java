package com.chandu.movie_service;

import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.Matchers.is;






import com.chandu.movie_service.model.Movie;
import com.chandu.movie_service.repo.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.var;

@SpringBootTest
@AutoConfigureMockMvc
class MovieServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	/**
	 * @throws Exception
	 */
	@Autowired
	MovieRepository movieRepository;


	@BeforeEach
	void cleanup()
	{
		movieRepository.deleteAllInBatch();
	}


	@Test
	void givenMovie_whenCreateMovie_thenReturnSavedMovie() throws Exception
	{	//given movie
		Movie movie = new Movie();
		movie.setName("rrr");
		movie.setDirector("SS.rajamouli");
		movie.setActors(List.of("ramcharan","ntr","ajay devgun"));

		//when create movie
		var response = mockMvc.perform(post("/movies")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(movie)));
				
				response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.name", is(movie.getName())))
				.andExpect(jsonPath("$.director", is(movie.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie.getActors())));
	}

	@Test
    void givenMovieId_whenFetchMovie_thenReturnMovie() throws Exception {
        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt"));

        Movie savedMovie = movieRepository.save(movie);

        // When
        var response = mockMvc.perform(get("/movies/" + savedMovie.getId()));

        //Then verify saved movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }


    @Test
void givenSavedMovie_WhenUpdateMovie_thenMovieUpdatedInDb() throws Exception {
    // Given: Create and save a movie
    Movie movie = new Movie();
    movie.setName("RRR");
    movie.setDirector("SS Rajamouli");
    movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt"));

    Movie savedMovie = movieRepository.save(movie);
    Long id = savedMovie.getId();

    // Update movie actors
    movie.setActors(List.of("NTR", "Ram Charan", "Alia Bhatt", "Ajay Devgan"));

    // When: Perform PUT request to update the movie
    var updateResponse = mockMvc.perform(put("/movies/" + id)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(movie)));

    // Then: Verify the update response
    updateResponse.andDo(print())
                  .andExpect(status().isOk());

    // When: Fetch the updated movie
    var fetchResponse = mockMvc.perform(get("/movies/" + id));

    // Then: Verify the fetched movie
    fetchResponse.andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name").value(movie.getName()))
                 .andExpect(jsonPath("$.director").value(movie.getDirector()))
                 .andExpect(jsonPath("$.actors").isArray())
                 .andExpect(jsonPath("$.actors[0]").value("NTR"))
                 .andExpect(jsonPath("$.actors[1]").value("Ram Charan"))
                 .andExpect(jsonPath("$.actors[2]").value("Alia Bhatt"))
                 .andExpect(jsonPath("$.actors[3]").value("Ajay Devgan"));
}


    @Test
    void givenMovie_whenDeleteRequest_thenMovieRemovedFromDb() throws Exception {
        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt"));

        Movie savedMovie = movieRepository.save(movie);
        Long id = savedMovie.getId();

        //Then
        mockMvc.perform(delete("/movies/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(movieRepository.findById(id).isPresent());

    }

	
		
			

}
