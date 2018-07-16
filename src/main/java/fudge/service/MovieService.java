package fudge.service;

import fudge.model.movielens.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<Movie> getMovies(Pageable pageable);

    Page<Movie> findMoviesByTitle(String title, Pageable pageable);

    Movie getMovie(Integer movieId);

    String getMovieDetails(Integer movieId);
}
