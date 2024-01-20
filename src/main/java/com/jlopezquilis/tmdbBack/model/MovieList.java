package com.jlopezquilis.tmdbBack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MovieList {
    @JsonProperty("page")
    Integer page;

    @JsonProperty("total_results")
    Long total_results;

    @JsonProperty("total_pages")
    Long total_pages;

    @JsonProperty("results")
    List<Movie> results;

    public Long getTotalResults() {
        return total_results;
    }

    public Long getTotalPages() {
        return total_pages;
    }

}
