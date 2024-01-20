package com.jlopezquilis.tmdbBack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Movie {
    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("genre_ids")
    private List<Long> genres;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private float popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("title")
    private String title;

    @JsonProperty("vote_average")
    private float voteAverage;

    @JsonProperty("budget")
    private long budget;

    @JsonProperty("revenue")
    private long revenue;

    @JsonProperty("rating")
    private float userRating;

    @JsonProperty("vote_count")
    private int voteCount;

    @JsonProperty("status")
    private String status;
}
