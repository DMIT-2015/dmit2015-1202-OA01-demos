package dmit2015.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dmit2015.entity.Movie;

@ApplicationScoped
@Transactional
public class MovieRepository {

    @PersistenceContext //(unitName = "h2database-jpa-pu")
    private EntityManager em;

    public void add(Movie newMovie) {
        em.persist(newMovie);
    }

    public void update(Movie updatedMovie) {
        Optional<Movie> optionalMovie = findById(updatedMovie.getId());
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setPrice(updatedMovie.getPrice());
            existingMovie.setRating(updatedMovie.getRating());
            existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
            em.merge(existingMovie);
            em.flush();
        }
    }

    public void remove(Movie existingMovie) {
        em.remove(em.merge(existingMovie));
        em.flush();
    }

    public void remove(Long movieID) {
        Optional<Movie> optionalMovie = findById(movieID);
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            remove(existingMovie);
        }
    }

    public Optional<Movie> findById(Long movieID) {
        Optional<Movie> optionalMovie = Optional.empty();
        try {
            Movie querySingleResult = em.find(Movie.class, movieID);
            if (querySingleResult != null) {
                optionalMovie = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalMovie;
    }

    public List<Movie> findAll() {
        return em.createQuery(
                "SELECT m FROM Movie m  "
                , Movie.class)
                .getResultList();
    }
}