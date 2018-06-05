package hello.payload;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class RatingRequest {
    @NotNull
    private int movieId;
    @DecimalMin("0.5")
    @DecimalMax("5")
    private double rating;
}
