package fudge.service;

import fudge.model.rating.AverageRating;
import fudge.model.rating.PredictedRating;
import fudge.model.rating.Rating;
import fudge.model.rating.RatingKey;
import fudge.payload.RatingRequest;
import fudge.repository.AverageRatingsRepository;
import fudge.repository.PredictedRatingsRepository;
import fudge.repository.RatingsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.OptionalDouble;

import static java.lang.Math.toIntExact;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@Slf4j
public class RatingsServiceImpl implements RatingsService {
    private RatingsRepository ratingsRepository;
    private AverageRatingsRepository averageRatingsRepository;
    private PredictedRatingsRepository predictedRatingsRepository;
    private UserService userService;

    @Override
    public ResponseEntity<List<Rating>> getRatings(Integer movieId) {
        List<Rating> ratings = ratingsRepository.findAllByRatingKey_MovieId(movieId);
        if (ratings.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ratings);
        }
    }

    @Override
    public ResponseEntity saveRating(RatingRequest ratingRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findIdByEmail(email);
        RatingKey ratingKey = new RatingKey(toIntExact(userId), ratingRequest.getMovieId());
        Rating rating = new Rating(ratingKey, ratingRequest.getRating(), toIntExact(Instant.now().getEpochSecond()));
        log.info("Add " + rating);
        ratingsRepository.save(rating);
        ratingsRepository.flush();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Pair<Double, Integer>> getAverageRatings(Integer movieId) {
        List<Rating> ratings = ratingsRepository.findAllByRatingKey_MovieId(movieId);
        OptionalDouble optionalAverage = ratings.stream().mapToDouble(Rating::getRating).average();
        if (optionalAverage.isPresent()) {
            return ResponseEntity.ok(Pair.of(optionalAverage.getAsDouble(), ratings.size()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Double> getAverageRating(Integer movieId) {
        AverageRating averageRating = averageRatingsRepository.findOne(movieId);
        if (isNull(averageRating)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(averageRating.getAverageRating());
        }
    }

    @Override
    public ResponseEntity<Double> getPredictedRating(Integer movieId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findIdByEmail(email);
        RatingKey ratingKey = new RatingKey(toIntExact(userId), movieId);
        PredictedRating predictedRating = predictedRatingsRepository.findOne(ratingKey);
        if (isNull(predictedRating)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(predictedRating.getRating());
        }
    }
}