package dmit2015.repository;

import dmit2015.config.ApplicationConfig;
import dmit2015.entity.Movie;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)         // This currently does not work properly with Arquillian
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
class MovieRepositoryArquillianIT {

    static Movie currentMovie;  // the Movie that is currently being added, find, update, or delete

    @Inject
    private MovieRepository _movieRepository;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
//                .addAsLibraries(pomFile.resolve("groupId:artifactId:version").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.h2database:h2:1.4.200").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:8.4.1.jre11").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc10:19.9.0.0").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core:5.3.20.Final").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate.validator:hibernate-validator:6.2.0.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(Movie.class, MovieRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Test
    void shouldThrowException() {
        Movie currentMovie = new Movie();
        ConstraintViolationException cve = assertThrows(
                ConstraintViolationException.class,
                () -> _movieRepository.add(currentMovie)
        );
//        _movieRepository.add(currentMovie);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Movie>> constraintViolations = validator.validate(currentMovie);
        assertEquals(4, constraintViolations.size());

    }

    @Order(2)
    @Test
    void shouldCreate() {
        currentMovie = new Movie();
        currentMovie.setGenre("Horror");
        currentMovie.setPrice(BigDecimal.valueOf(19.99));
        currentMovie.setRating("NC-17");
        currentMovie.setTitle("The Return of the Java Master");
        currentMovie.setReleaseDate(LocalDate.parse("2021-01-21"));
        _movieRepository.add(currentMovie);

        Optional<Movie> optionalMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals("The Return of the Java Master", existingMovie.getTitle());
        assertEquals("Horror", existingMovie.getGenre());
        assertEquals(19.99, existingMovie.getPrice().doubleValue());
        assertEquals("NC-17", existingMovie.getRating());
        assertEquals(LocalDate.parse("2021-01-21").toString(), existingMovie.getReleaseDate().toString());
    }

    @Order(3)
    @Test
    void shouldFindOne() {
        final Long movieId = currentMovie.getId();
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals("The Return of the Java Master", existingMovie.getTitle());
        assertEquals("Horror", existingMovie.getGenre());
        assertEquals(19.99, existingMovie.getPrice().doubleValue());
        assertEquals("NC-17", existingMovie.getRating());
        assertEquals(LocalDate.parse("2021-01-21").toString(), existingMovie.getReleaseDate().toString());
    }

    @Order(1)
    @Test
    void shouldFindAll() {
        List<Movie> queryResultList = _movieRepository.findAll();
        assertEquals(4, queryResultList.size());

        Movie firstMovie = queryResultList.get(0);
        assertEquals("When Harry Met Sally", firstMovie.getTitle());
        assertEquals("Romantic Comedy", firstMovie.getGenre());
        assertEquals(7.99, firstMovie.getPrice().doubleValue());
        assertEquals("G", firstMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1989-02-12", formatter).toString(), firstMovie.getReleaseDate().toString());

        Movie lastMovie = queryResultList.get(queryResultList.size() - 1);
        assertEquals("Rio Bravo", lastMovie.getTitle());
        assertEquals("Western", lastMovie.getGenre());
        assertEquals(7.99, lastMovie.getPrice().doubleValue());
        assertEquals("PG-13", lastMovie.getRating());
        assertEquals(LocalDate.parse("1959-04-15", formatter).toString(), lastMovie.getReleaseDate().toString());

        queryResultList.forEach(System.out::println);
    }

    @Order(4)
    @Test
    void shouldUpdate() {
        currentMovie.setTitle("Ghostbusters 2016");
        currentMovie.setPrice(BigDecimal.valueOf(16.99));
        currentMovie.setReleaseDate(LocalDate.parse("2016-07-15"));
        _movieRepository.update(currentMovie);

        Optional<Movie> optionalUpdatedMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalUpdatedMovie.isPresent());
        Movie updatedMovie = optionalUpdatedMovie.get();
        assertNotNull(updatedMovie);
        assertEquals(currentMovie.getTitle(), updatedMovie.getTitle());
        assertEquals(currentMovie.getPrice(), updatedMovie.getPrice());
        assertEquals(currentMovie.getReleaseDate(), updatedMovie.getReleaseDate());
        assertEquals(currentMovie.getGenre(), updatedMovie.getGenre());
    }

    @Order(5)
    @Test
    void shouldDelete() {
        final Long movieId = currentMovie.getId();
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        _movieRepository.remove(existingMovie.getId());
        optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isEmpty());
    }
}