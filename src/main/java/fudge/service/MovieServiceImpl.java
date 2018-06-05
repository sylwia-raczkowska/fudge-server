package fudge.service;

import fudge.model.movielens.Movie;
import fudge.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return movieRepository.findOne(movieId);
    }

    @Override
    public String getMovieDetails(Integer movieId) {
        String apiKey = env.getProperty("omdb.key");
        String imdbId = getMovie(movieId).getLinks().getImdbId();
        return restTemplate.getForObject("http://www.omdbapi.com/?i=tt0" + imdbId + "&apikey=" + apiKey, String.class);
    }

}
