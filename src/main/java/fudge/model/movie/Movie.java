package fudge.model.movie;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    private Integer movieId;
    private String title;
    private String genres;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Links links;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Details details;
}
