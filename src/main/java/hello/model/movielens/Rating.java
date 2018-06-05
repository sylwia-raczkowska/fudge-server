package hello.model.movielens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Entity
@Data
@Table(name = "ratings")
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @EmbeddedId
    private RatingKey ratingKey;
    private Double rating;
    private Integer timestamp;
}
