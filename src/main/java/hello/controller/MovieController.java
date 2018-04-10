package hello.controller;

import hello.model.Movie;
import hello.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping(path = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovie(@PathVariable Integer movieId) {
        return movieRepository.findOne(movieId);
    }
}
