package hello.controller;

import hello.model.movielens.Movie;
import hello.model.movielens.Rating;
import hello.repository.MovieRepository;
import hello.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieRepository movieRepository;
    private RatingsRepository ratingsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Autowired
    public MovieController(MovieRepository movieRepository, RatingsRepository ratingsRepository) {
        this.movieRepository = movieRepository;
        this.ratingsRepository = ratingsRepository;
    }

    @GetMapping
    public Page<Movie> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable Integer movieId) {
        return movieRepository.findOne(movieId);
    }

    @GetMapping(value = "/{movieId}/details", produces = "application/json")
    public String getMovieDetails(@PathVariable Integer movieId) {
        String apiKey = env.getProperty("omdb.key");
        String imdbId = getMovie(movieId).getLinks().getImdbId();
        return restTemplate.getForObject("http://www.omdbapi.com/?i=tt0" + imdbId + "&apikey=" + apiKey, String.class);
    }

    @GetMapping("/{movieId}/ratings")
    public List<Rating> getRatings(@PathVariable Integer movieId) {
        return ratingsRepository.findAllByRatingKey_MovieId(movieId);
    }

    @GetMapping("/{movieId}/ratings/average")
    public Pair<Double, Integer> getAverageRatings(@PathVariable Integer movieId) {
        List<Rating> ratings = ratingsRepository.findAllByRatingKey_MovieId(movieId);
        OptionalDouble optionalAverage = ratings.stream().mapToDouble(Rating::getRating).average();
        Double average = optionalAverage.isPresent() ? optionalAverage.getAsDouble() : 0;
        return Pair.of(average, ratings.size());
    }
}