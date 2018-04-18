package hello.model.movielens;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "links")
public class Links {
    @Id
    private Integer movieId;
    private String imdbId;
    private String tmdbId;
}
