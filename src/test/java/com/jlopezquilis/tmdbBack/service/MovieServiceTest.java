package com.jlopezquilis.tmdbBack.service;

import com.jlopezquilis.tmdbBack.TestSecurityConfig;
import com.jlopezquilis.tmdbBack.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void testFetchMovieList() {
        List<Movie> movieList = movieService.fetchMovieList();
        Assertions.assertNotNull(movieList);
        Assertions.assertFalse(movieList.isEmpty());
    }

    @Test
    public void testFetchMovieById() {
        Long movieId = 20L;
        Movie movie = movieService.fetchMovieById(movieId);
        Assertions.assertEquals(movieId, movie.getId());

        movieId = 1L;
        movie = movieService.fetchMovieById(movieId);
        Assertions.assertNull(movie);
    }

    @Test
    public void testFetchMoviesByQuery() {
        String query = "The Shawshank Redemption";
        List<Movie> movies = movieService.fetchMoviesByName(query);
        Assertions.assertFalse(movies.isEmpty());
        for (Movie movie : movies) {
            Assertions.assertTrue(isTitleContained(query, movie.getTitle()));
        }

        query = "Lorem ipsum dolor sit amet.";
        movies = movieService.fetchMoviesByName(query);
        Assertions.assertTrue(movies.isEmpty());
    }

    public static boolean isTitleContained(String title, String otherTitle) {
        String[] titleWords = title.split("\\s+");

        for (String titleWord : titleWords)
            if (!otherTitle.contains(titleWord)) return false;
        return true;
    }
}