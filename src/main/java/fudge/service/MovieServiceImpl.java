package fudge.service;

import fudge.model.movie.Details;
import fudge.model.movie.Movie;
import fudge.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private Environment env;
    private RestTemplate restTemplate;

    @Override
    public Page<Movie> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Movie getMovie(Integer movieId) {
        Movie movie = movieRepository.findOne(movieId);
        if (Objects.isNull(movie.getDetails())){
            String imdbId = movie.getLinks().getImdbId();
            Details details = getMovieDetails(imdbId);
            if ("True".equals(details.getResponse()))
                movie.setDetails(details);
        }
        return movie;
    }

    private Details getMovieDetails(String imdbId) {
        String apiKey = env.getProperty("omdb.key");
        return restTemplate.getForObject("http://www.omdbapi.com/?i=tt0" + imdbId + "&apikey=" + apiKey, Details.class);
    }

}
