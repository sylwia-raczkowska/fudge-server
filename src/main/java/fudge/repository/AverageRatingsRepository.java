package fudge.repository;

import fudge.model.rating.AverageRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AverageRatingsRepository extends JpaRepository<AverageRating, Integer> {
}
