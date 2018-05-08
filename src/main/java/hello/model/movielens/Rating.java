package hello.model.movielens;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "ratings")
public class Rating {
    @EmbeddedId
    private RatingKey ratingKey;
    private Double rating;
    private Integer timestamp;
}
