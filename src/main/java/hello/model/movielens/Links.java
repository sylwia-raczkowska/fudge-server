package hello.model.movielens;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "links")
public class Links {
    @Id
    @JsonIgnore
    private Integer movieId;
    private String imdbId;
    private String tmdbId;
}
