package fudge.service;

import fudge.model.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<Movie> getMovies(Pageable pageable);

    Movie getMovie(Integer movieId);
}
