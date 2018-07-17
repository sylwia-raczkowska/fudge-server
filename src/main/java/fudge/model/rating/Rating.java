package fudge.model.rating;

import fudge.model.rating.RatingKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "ratings")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rating {
    @EmbeddedId
    private RatingKey ratingKey;
    private Double rating;
    private Integer timestamp;
}
