package fudge.repository;

import fudge.model.rating.PredictedRating;
import fudge.model.rating.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictedRatingsRepository extends JpaRepository<PredictedRating, RatingKey> {
}