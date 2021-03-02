package dmit2015.view;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentMovieListController")    // Allows you to access an instance of this class using EL #{currentMovieListController}
@ViewScoped // must implement Serializable interface
            // Keep this object in memory if the next request is for the same view
public class MovieListController implements Serializable {

    @Inject
    private MovieRepository _movieRepository;

    @Getter
    private List<Movie> movies;

    @PostConstruct  // after Dependency Injection is complete
    void init() {
        movies = _movieRepository.findAll();
    }

}
