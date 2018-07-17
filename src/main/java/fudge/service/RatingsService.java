package fudge.service;

import fudge.model.rating.Rating;
import fudge.payload.RatingRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatingsService {
    List<Rating> getRatings(Integer movieId);

    Pair<Double, Integer> getAverageRatings(Integer movieId);

    ResponseEntity saveRating(RatingRequest ratingRequest);
}
