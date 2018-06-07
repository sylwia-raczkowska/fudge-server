package fudge.service;

import fudge.model.movielens.Rating;
import fudge.model.movielens.RatingKey;
import fudge.payload.RatingRequest;
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

@Service
@AllArgsConstructor
@Slf4j
public class RatingsServiceImpl implements RatingsService {
    private RatingsRepository ratingsRepository;
    private UserService userService;

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

}
