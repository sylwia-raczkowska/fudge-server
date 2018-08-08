package fudge.model.rating;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "averageMovieRating")
public class AverageRating {
    @Id
    private Integer movieId;
    private Double averageRating;
}