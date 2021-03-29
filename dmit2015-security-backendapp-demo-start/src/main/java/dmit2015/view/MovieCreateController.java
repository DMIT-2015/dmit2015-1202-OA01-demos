package dmit2015.view;

import dmit2015.entity.Movie;
import dmit2015.producer.LoggerProducer;
import dmit2015.repository.MovieRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

@Named("currentMovieCreateController")
@RequestScoped
public class MovieCreateController {

//    private Logger logger = Logger.getLogger(MovieCreateController.class.getName());
    @Inject
    private Logger logger;

    @Inject
    private MovieRepository _movieRepository;

    @Getter
    private Movie newMovie = new Movie();

    public String onCreateNew() {
        logger.info("entering onCreateNew method");
        String nextPage = "";
        try {
            _movieRepository.add(newMovie);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (RuntimeException e) {
            Messages.addGlobalWarn(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.fine(e.getMessage());
            Messages.addGlobalError("Create was not successful.");
        }
        logger.info("exiting onCreateNewMethod");
        return nextPage;
    }

}