package fudge.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Data
@Table(name = "movie_details")
public class Details {
    @Id
    @JsonIgnore
    private Integer movieId;
    private String imdbRating;
    private String imdbVotes;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Poster")
    private String imageUrl;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Writer")
    private String writer;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Plot")
    private String description;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Awards")
    private String awards;
    @JsonProperty("Production")
    private String production;
    @JsonProperty("Website")
    private String website;
    @Transient
    @JsonProperty(value = "Response", access = JsonProperty.Access.WRITE_ONLY)
    private String response;
}