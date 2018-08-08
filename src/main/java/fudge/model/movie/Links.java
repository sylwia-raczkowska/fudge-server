package fudge.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Links {
    @Id
    @JsonIgnore
    private Integer movieId;
    private String imdbId;
    private String tmdbId;
}
