package fudge.repository;

import fudge.model.movielens.Rating;
import fudge.model.movielens.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, RatingKey> {
    List<Rating> findAllByRatingKey_MovieId(Integer movieId);
}