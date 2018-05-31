package hello.service;

import hello.model.movielens.Rating;
import org.springframework.data.util.Pair;

import java.util.List;

public interface RatingsService {
    List<Rating> getRatings(Integer movieId);

    Pair<Double, Integer> getAverageRatings(Integer movieId);
}
