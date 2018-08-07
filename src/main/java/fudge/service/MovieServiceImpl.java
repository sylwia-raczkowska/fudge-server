package fudge.service;

import fudge.model.movie.Details;
import fudge.model.movie.Movie;
import fudge.model.rating.Rating;
import fudge.model.rating.RatingKey;
import fudge.repository.MovieDetailsRepository;
import fudge.repository.MovieRepository;
import fudge.repository.RatingsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static java.lang.Math.toIntExact;

@Service
@AllArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
	private MovieRepository movieRepository;
	private MovieDetailsRepository detailsRepository;
	private Environment env;
	private RestTemplate restTemplate;
	private UserService userService;
	private RatingsRepository ratingsRepository;
	private static final String TRUE = "True";
	private static final String APIKEY = "&apikey=";
	private static final String BASE_URL = "http://www.omdbapi.com/?i=tt";
	private static final String OMDB_KEY = "omdb.key";
	private static final String ZEROS = "0000000";

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
	public ResponseEntity<Movie> getMovie(Integer movieId) {
		Movie movie = movieRepository.findOne(movieId);

		if (Objects.isNull(movie)) {
		    return ResponseEntity.notFound().build();
        } else {
            fillMovieRate(movie);
            if (Objects.isNull(movie.getDetails())) {
                setMovieDetails(movie);
            }
            return ResponseEntity.ok(movie);
        }
	}

	private void fillMovieRate(Movie movie) {
		Long userId = null;
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			userId = userService.findIdByEmail(email);
		} catch (UsernameNotFoundException ex) {
			log.info("Username not found");
		}
		Integer id = userId != null ? toIntExact(userId) : null;
		Rating rating = ratingsRepository.findAllByRatingKey(new RatingKey(id, movie.getMovieId()));
		movie.setUserRate(rating != null ? rating.getRating() : null);
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
