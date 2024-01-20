package com.jlopezquilis.tmdbBack.service;

import com.jlopezquilis.tmdbBack.model.Movie;
import com.jlopezquilis.tmdbBack.model.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
public class MovieService {
    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${tmdb.base.url}")
    private String tmdbBaseUrl;

    @Value("${tmdb.top.rated.movie.url}")
    private String topRatedMovieUrl;

    @Value("${tmdb.search.movie.url}")
    private String tmdbSearchMovieUrl;

    @Value("${tmdb.movie.id.url}")
    private String tmdbMovieIdUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "RestTemplate must not be null!");
        this.restTemplate = restTemplate;
    }

    public URI getURI(String patternUrl, String [] exp) {
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(patternUrl).build()
                        .expand((Object[]) exp)
                        .encode();

        return uriComponents.toUri();
    }

    public List<Movie> fetchMovieList() {
        URI uri = getURI(topRatedMovieUrl, new String[] {tmdbBaseUrl, tmdbApiKey});
        MovieList movieList = restTemplate.getForObject(uri, MovieList.class);
        if(movieList == null) return Collections.emptyList();
        return movieList.getResults();
    }

    public Movie fetchMovieById(Long id){

        URI uri = getURI(tmdbMovieIdUrl, new String [] {tmdbBaseUrl, id.toString(), tmdbApiKey});
        try
        {
            return restTemplate.getForObject(uri, Movie.class);
        }
        catch(HttpClientErrorException e)
        {
            return null;
        }
    }

    public List<Movie> fetchMoviesByQuery(String query) {

        URI uri = getURI(tmdbSearchMovieUrl, new String [] {tmdbBaseUrl, tmdbApiKey, query});
        MovieList movieResults = restTemplate.getForObject(uri, MovieList.class);
        if(movieResults == null) return Collections.emptyList();
        return movieResults.getResults();
    }
}
