package fudge.service;

import fudge.model.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovieService {
    Page<Movie> getMovies(Pageable pageable);

    Page<Movie> findMoviesByTitle(String title, Pageable pageable);

    ResponseEntity<Movie> getMovie(Integer movieId);

    ResponseEntity<List<Movie>> getTop100Movies();
}
