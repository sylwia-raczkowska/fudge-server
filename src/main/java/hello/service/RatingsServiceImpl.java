package hello.service;

import hello.model.movielens.Rating;
import hello.model.movielens.RatingKey;
import hello.payload.RatingRequest;
import hello.repository.RatingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.Instant;
import java.util.List;
import java.util.OptionalDouble;

import static java.lang.Math.toIntExact;

@Service
@AllArgsConstructor
public class RatingsServiceImpl implements RatingsService {
    private RatingsRepository ratingsRepository;

    @Override
    public List<Rating> getRatings(Integer movieId) {
        return ratingsRepository.findAllByRatingKey_MovieId(movieId);
    }

    @Override
    public Pair<Double, Integer> getAverageRatings(Integer movieId) {
        List<Rating> ratings = ratingsRepository.findAllByRatingKey_MovieId(movieId);
        OptionalDouble optionalAverage = ratings.stream().mapToDouble(Rating::getRating).average();
        Double average = optionalAverage.isPresent() ? optionalAverage.getAsDouble() : 0;
        return Pair.of(average, ratings.size());
    }

    @Override
    public ResponseEntity saveRating(RatingRequest ratingRequest, RequestHeader header) {
        //TODO wyciaganie usera z headera (tokena)
        RatingKey ratingKey = new RatingKey(ratingRequest.getUserId(), ratingRequest.getMovieId());
        Rating rating = new Rating(ratingKey, ratingRequest.getRating(), toIntExact(Instant.now().getEpochSecond()));

        ratingsRepository.save(rating);
        ratingsRepository.flush();
        return ResponseEntity.ok().build();
    }

}
