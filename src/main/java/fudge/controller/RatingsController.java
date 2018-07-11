package fudge.controller;

import fudge.model.rating.Rating;
import fudge.payload.RatingRequest;
import fudge.service.RatingsService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity addRating(@Valid @RequestBody RatingRequest ratingRequest) {
        return ratingsService.saveRating(ratingRequest);
    }

    @GetMapping("/average/{movieId}")
    public Pair<Double, Integer> getAverageRatings(@PathVariable Integer movieId) {
        return ratingsService.getAverageRatings(movieId);
    }
}