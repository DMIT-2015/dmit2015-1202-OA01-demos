package dmit2015.repository;

import common.config.ApplicationConfig;
import dmit2015.entity.Movie;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // Allows use to specify the order of the test using @Order annotation
@TestInstance(TestInstance.Lifecycle.PER_CLASS)         // Allows us to share data between test methods - does not work with current version of Arquillian
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
class MovieRepositoryArquillianIT {

    @Inject
    private MovieRepository _movieRepository;

//
//    private Long testEditId = 9L;   // requires you to add @TestInstance(TestInstance.Lifecycle.PER_CLASS) before class
//
    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
                .addAsLibraries(pomFile.resolve("com.h2database:h2:1.4.200").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:8.4.1.jre11").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc10:19.9.0.0").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core:5.4.27.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(Movie.class, MovieRepository.class)
//                .addPackage("dmit2015.entity")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }
//
//    @Order(4)
    @Test
    void findAll() {
        List<Movie> queryResultList = _movieRepository.findAll();
        assertEquals(4, queryResultList.size());
        // Retrieve the first record
        Movie movie1 = queryResultList.get(0);
        assertEquals("When Harry Met Sally", movie1.getTitle());
        // Retrieve the last record
        Movie movie2 = queryResultList.get(3);
        assertEquals("Rio Bravo", movie2.getTitle());

        queryResultList.forEach(singleItem -> {
//            System.out.println(singleItem.getId() + ":" + singleItem.getTitle());
            System.out.println(singleItem);
        });
    }
//
//    @Order(1)
    @Test
    void findOne() {
        Optional<Movie> optionalMovie = _movieRepository.findById(1L);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals("When Harry Met Sally", existingMovie.getTitle());
        assertEquals("Romantic Comedy", existingMovie.getGenre());
        assertEquals(7.99, existingMovie.getPrice().doubleValue());
        assertEquals("G", existingMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals(LocalDate.parse("1989-02-12", formatter).toString(), existingMovie.getReleaseDate().toString());

    }
//
//    @Order(2)
//    @Test
//    void create() {
//        Movie newMovie = new Movie();
//        newMovie.setGenre("Horror");
//        newMovie.setPrice(BigDecimal.valueOf(19.99));
//        newMovie.setRating("PG-13");
//        newMovie.setTitle("The Return of the Java Master");
//        newMovie.setReleaseDate(LocalDate.parse("2021-01-21"));
//        _movieRepository.add(newMovie);
//        testEditId = newMovie.getId();
//
//        Optional<Movie> optionalMovie = _movieRepository.findById(newMovie.getId());
//        assertTrue(optionalMovie.isPresent());
//        Movie existingMovie = optionalMovie.get();
//        assertNotNull(existingMovie);
//        assertEquals("The Return of the Java Master", existingMovie.getTitle());
//        assertEquals("Horror", existingMovie.getGenre());
//        assertEquals(19.99, existingMovie.getPrice().doubleValue());
//        assertEquals("PG-13", existingMovie.getRating());
//        assertEquals(LocalDate.parse("2021-01-21").toString(), existingMovie.getReleaseDate().toString());
//    }
//
//    @Order(3)
//    @Test
//    void update() {
//        Optional<Movie> optionalMovie = _movieRepository.findById(testEditId);
//        assertTrue(optionalMovie.isPresent());
//        Movie existingMovie = optionalMovie.get();
//        assertNotNull(existingMovie);
//        existingMovie.setTitle("The NEW Java Master");
//        existingMovie.setGenre("Comedy");
//        _movieRepository.update(existingMovie);
//
//        Movie updatedMovie = _movieRepository.findById(testEditId).get();
//        assertNotNull(updatedMovie);
//        assertEquals(existingMovie.getTitle(), updatedMovie.getTitle());
//        assertEquals(existingMovie.getGenre(), updatedMovie.getGenre());
//    }
//
//    @Order(5)
//    @Test
//    void delete() {
//        Optional<Movie> optionalMovie = _movieRepository.findById(testEditId);
//        assertTrue(optionalMovie.isPresent());
//        Movie existingMovie = optionalMovie.get();
//        assertNotNull(existingMovie);
//        _movieRepository.remove(existingMovie.getId());
//        optionalMovie = _movieRepository.findById(testEditId);
//        assertNull(optionalMovie.isEmpty());
//    }
}