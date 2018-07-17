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
    public static final String APIKEY = "&apikey=";
    public static final String BASE_URL = "http://www.omdbapi.com/?i=tt";
    public static final String OMDB_KEY = "omdb.key";
    public static final String ZEROS = "0000000";
    public static final String TRUE = "True";
    private MovieRepository movieRepository;
    private MovieDetailsRepository detailsRepository;
    private Environment env;
    private RestTemplate restTemplate;

    @Override
    public Page<Movie> getMovies(Pageable pageable) {
        Page<Movie> page = movieRepository.findAll(pageable);
        page.forEach(movie -> {
            if (Objects.isNull(movie.getDetails()))
                setMovieDetails(movie);
        });
        return movieRepository.findAll(pageable);
    }

    @Override
    public Page<Movie> findMoviesByTitle(String title, Pageable pageable) {
        return movieRepository.findByTitleIgnoreCaseContaining(title, pageable);
    }

    @Override
    public Movie getMovie(Integer movieId) {
        Movie movie = movieRepository.findOne(movieId);
        if (Objects.isNull(movie.getDetails()))
            setMovieDetails(movie);
        return movie;
    }

    private void setMovieDetails(Movie movie) {
        String imdbId = movie.getLinks().getImdbId();
        Details details = getMovieDetails(imdbId);
        if (TRUE.equals(details.getResponse())) {
            details.setMovieId(movie.getMovieId());
            saveMovieDetails(details);
            movie.setDetails(details);
        }
    }

    private Details getMovieDetails(String imdbId) {
        String apiKey = env.getProperty(OMDB_KEY);
        String formattedImdbId = (ZEROS + imdbId).substring(imdbId.length());

        String url = new StringBuilder()
                .append(BASE_URL)
                .append(formattedImdbId)
                .append(APIKEY)
                .append(apiKey)
                .toString();

        return restTemplate.getForObject(url, Details.class);
    }

    private void saveMovieDetails(Details movieDetails) {
        log.info("Add " + movieDetails);
        detailsRepository.save(movieDetails);
        detailsRepository.flush();
    }

}
