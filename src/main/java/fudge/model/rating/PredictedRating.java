package fudge.model.rating;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "ratings_predictions")
public class PredictedRating {
    @EmbeddedId
    private RatingKey ratingKey;
    private Double rating;
}