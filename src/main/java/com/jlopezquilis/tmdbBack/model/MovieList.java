package com.jlopezquilis.tmdbBack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieList {
    @JsonProperty("page")
    Integer page;

    @JsonProperty("total_results")
    Long total_results;

    @JsonProperty("total_pages")
    Long total_pages;

    @JsonProperty("results")
    List<Movie> results;

    public Integer getPage() {
        return page;
    }

    public Long getTotalResults() {
        return total_results;
    }

    public Long getTotalPages() {
        return total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
