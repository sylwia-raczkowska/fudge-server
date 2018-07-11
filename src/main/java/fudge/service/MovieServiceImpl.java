package fudge.service;

import fudge.model.movie.Details;
import fudge.model.movie.Movie;
import fudge.repository.MovieDetailsRepository;
import fudge.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private MovieDetailsRepository detailsRepository;
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
            if ("True".equals(details.getResponse())) {
                details.setMovieId(movieId);
                saveMovieDetails(details);
                movie.setDetails(details);
            }
        }
        return movie;
    }

    private Details getMovieDetails(String imdbId) {
        String apiKey = env.getProperty("omdb.key");
        return restTemplate.getForObject("http://www.omdbapi.com/?i=tt0" + imdbId + "&apikey=" + apiKey, Details.class);
    }

    private void saveMovieDetails (Details movieDetails) {
        log.info("Add " + movieDetails);
        detailsRepository.save(movieDetails);
        detailsRepository.flush();
    }

}
