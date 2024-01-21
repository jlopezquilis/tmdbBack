package com.jlopezquilis.tmdbBack.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jlopezquilis.tmdbBack.configuration.WebSecurityConfig;
import com.jlopezquilis.tmdbBack.model.Movie;
import com.jlopezquilis.tmdbBack.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@Import(WebSecurityConfig.class)
public class MovieControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserDetailsService userDetailsService;

    @Mock
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.userDetailsService = userDetailsServiceForTest();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    private UserDetailsService userDetailsServiceForTest() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password("password")
                .roles("USER")
                .build());
        return manager;
    }

    @Test
    @WithMockUser(username = "admin", password = "password")
    public void testGetTopMovies() throws Exception {
        // Arrange
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());
        when(movieService.fetchMovieList()).thenReturn(movies);

        // Act & Assert
        mockMvc.perform(get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", password = "password")
    public void testGetMovieByIdFound() throws Exception {
        // Arrange
        long movieId = 200;
        Movie movie = new Movie();
        when(movieService.fetchMovieById(movieId)).thenReturn(movie);

        // Act & Assert
        mockMvc.perform(get("/movies/" + movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("200"));
    }

    @Test
    @WithMockUser(username = "admin", password = "password")
    public void testGetMovieByIdNotFound() throws Exception {
        // Arrange
        long movieId = -5;
        when(movieService.fetchMovieById(movieId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/movies/" + movieId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "password")
    public void testGetMovieSearchResult() throws Exception {
        // Arrange
        String name = "The Shawshank Redemption";
        List<Movie> movies = Arrays.asList(new Movie());
        when(movieService.fetchMoviesByName(name)).thenReturn(movies);

        // Act & Assert
        mockMvc.perform(get("/movies/search/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", name))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].original_title").value("The Shawshank Redemption"));
    }
}
