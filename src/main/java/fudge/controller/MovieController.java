package fudge.controller;

import fudge.model.movie.Movie;
import fudge.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;

    @GetMapping
    public Page<Movie> getMovies(Pageable pageable) {
        return movieService.getMovies(pageable);
    }

    @GetMapping("/title/{title}")
    public Page<Movie> findMoviesByTitle(@PathVariable String title, Pageable pageable) {
        return movieService.findMoviesByTitle(title, pageable);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer movieId) {
        return movieService.getMovie(movieId);
    }
}