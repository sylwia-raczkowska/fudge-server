package hello.controller;

import hello.model.movielens.Movie;
import hello.model.movielens.Rating;
import hello.repository.MovieRepository;
import hello.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieRepository movieRepository;
    private RatingsRepository ratingsRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Autowired
    public MovieController(MovieRepository movieRepository, RatingsRepository ratingsRepository) {
        this.movieRepository = movieRepository;
        this.ratingsRepository = ratingsRepository;
    }

    @RequestMapping("/{movieId}")
    public Movie getMovie(@PathVariable Integer movieId) {
        return movieRepository.findOne(movieId);
    }

    @RequestMapping(value = "/{movieId}/details", produces = "application/json")
    public String getMovieDetails(@PathVariable Integer movieId) {
        String apiKey = env.getProperty("omdb.key");
        String imdbId = getMovie(movieId).getLinks().getImdbId();
        return restTemplate.getForObject("http://www.omdbapi.com/?i=tt0" + imdbId + "&apikey=" + apiKey, String.class);
    }

    @RequestMapping("/{movieId}/ratings")
    public List<Rating> getRatings(@PathVariable Integer movieId) {
        return ratingsRepository.findAllByRatingKey_MovieId(movieId);
    }
}
