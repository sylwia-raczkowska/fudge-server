package hello.service;

import hello.model.movielens.Rating;
import hello.model.movielens.RatingKey;
import hello.payload.RatingRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface RatingsService {
    List<Rating> getRatings(Integer movieId);

    Pair<Double, Integer> getAverageRatings(Integer movieId);

    ResponseEntity saveRating(RatingRequest ratingRequest, RequestHeader header);
}
