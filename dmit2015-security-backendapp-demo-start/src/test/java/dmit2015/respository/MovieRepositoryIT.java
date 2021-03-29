package dmit2015.respository;

import dmit2015.config.ApplicationConfig;
import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)     // currently does not work with arquillian-junit5-containerr 1.7.0.Alpha8
@ExtendWith(ArquillianExtension.class)              // Run with JUnit 5 instead of JUnit 4
class MovieRepositoryIT {

    static Movie currentMovie; // add static to shared data between test methods until arquillian-junit5-container TestInstance.Lifecycle.PER_CLASS bug is fixed

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
                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core:5.4.27.Final").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate.validator:hibernate-validator:6.2.0.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(Movie.class, MovieRepository.class)
//                .addPackage("dmit2015.entity")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Test
    void shouldFailToPersist() {
        Movie newMovie = new Movie();
//        _movieRepository.add(newMovie);
        ConstraintViolationException cve = assertThrows(
                ConstraintViolationException.class,
                () -> _movieRepository.add(newMovie)
        );

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Movie>> constraintViolations = validator.validate(newMovie);
        assertEquals(4, constraintViolations.size());
        constraintViolations.forEach(item -> {
            System.out.println(item.getMessage());
        });

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
    }

    @Order(4)
    @Test
    void shouldUpdate() {
        Optional<Movie> optionalMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        existingMovie.setTitle("Ghostbusters 2016");
        existingMovie.setPrice(BigDecimal.valueOf(16.99));
        existingMovie.setReleaseDate(LocalDate.parse("2016-07-15"));
        _movieRepository.update(existingMovie);

        Optional<Movie> optionalUpdatedMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalUpdatedMovie.isPresent());
        Movie updatedMovie = optionalUpdatedMovie.get();
        assertNotNull(updatedMovie);
        assertEquals(existingMovie.getTitle(), updatedMovie.getTitle());
        assertEquals(existingMovie.getPrice(), updatedMovie.getPrice());
        assertEquals(existingMovie.getReleaseDate(), updatedMovie.getReleaseDate());
        assertEquals(existingMovie.getGenre(), updatedMovie.getGenre());
    }

    @Test
    void shouldDelete() {
        Optional<Movie> optionalMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        _movieRepository.remove(existingMovie.getId());
        optionalMovie = _movieRepository.findById(currentMovie.getId());
        assertTrue(optionalMovie.isEmpty());
    }

}