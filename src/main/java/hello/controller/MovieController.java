package hello.controller;

import hello.model.movielens.Movie;
import hello.model.movielens.Rating;
import hello.service.MovieService;
import hello.service.RatingsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;

    @GetMapping
    public Page<Movie> getMovies(Pageable pageable) {
        return movieService.getMovies(pageable);
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable Integer movieId) {
        return movieService.getMovie(movieId);
    }

    @GetMapping(value = "/{movieId}/details", produces = "application/json")
    public String getMovieDetails(@PathVariable Integer movieId) {
        return movieService.getMovieDetails(movieId);
    }

}