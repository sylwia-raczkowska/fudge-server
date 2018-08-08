package fudge.repository;

import fudge.model.rating.AverageRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AverageRatingsRepository extends JpaRepository<AverageRating, Integer> {
    @Query("SELECT rating FROM AverageRating rating ORDER BY rating.averageRating DESC")
    List<AverageRating> findTop100();
}
