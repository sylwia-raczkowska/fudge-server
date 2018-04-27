package hello.model.movielens;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RatingKey implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "movie_id", nullable = false)
    private Integer movieId;
}
