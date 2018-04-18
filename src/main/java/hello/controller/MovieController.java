package hello.controller;

import hello.model.movielens.Movie;
import hello.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RequestMapping("/{movieId}")
    public Movie getMovie(@PathVariable Integer movieId) {
        return movieRepository.findOne(movieId);
    }
}
