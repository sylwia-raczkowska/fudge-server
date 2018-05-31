package hello.service;

import hello.model.movielens.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<Movie> getMovies(Pageable pageable);

    Movie getMovie(Integer movieId);

    String getMovieDetails(Integer movieId);
}
