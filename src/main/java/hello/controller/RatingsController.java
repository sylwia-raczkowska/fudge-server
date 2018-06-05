package hello.controller;

import hello.model.movielens.Rating;
import hello.model.movielens.RatingKey;
import hello.payload.RatingRequest;
import hello.service.RatingsService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

import static java.lang.Math.toIntExact;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingsController {
    private RatingsService ratingsService;

    @GetMapping("/{movieId}")
    public List<Rating> getRatings(@PathVariable Integer movieId) {
        return ratingsService.getRatings(movieId);
    }

    @PostMapping
    public ResponseEntity<?> addRating(@Valid @RequestBody RatingRequest ratingRequest, @RequestHeader RequestHeader header) {
    return ratingsService.saveRating(RatingRequest ratingRequest, header);
    }

    @GetMapping("/average/{movieId}")
    public Pair<Double, Integer> getAverageRatings(@PathVariable Integer movieId) {
        return ratingsService.getAverageRatings(movieId);
    }
}