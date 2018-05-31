package hello.service;

import hello.model.movielens.Rating;
import hello.repository.RatingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

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
}
