package dmit2015.resource;

import dmit2015.entity.Movie;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicroShedTest
public class MovieResourceMicroshedTestingIT {

    String testDataResourceLocation;

    @Container
    public static ApplicationContainer app = new ApplicationContainer()
            .withAppContextRoot("/dmit2015-instructor-jaxrs-demo")
            .withReadinessPath("/dmit2015-instructor-jaxrs-demo/webapi/movies")
            .withStartupTimeout(Duration.ofSeconds(120));

    @Order(1)
    @Test
    void shouldListAll() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/webapi/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        List<Movie> todos = jsonb.fromJson(jsonBody, new ArrayList<Movie>(){}.getClass().getGenericSuperclass());
        assertEquals(4, todos.size());
    }

    @Order(2)
    @Test
    void shouldCreate() {
        Movie currentMovie = new Movie();
        currentMovie.setGenre("Horror");
        currentMovie.setPrice(BigDecimal.valueOf(19.99));
        currentMovie.setRating("NC-17");
        currentMovie.setTitle("The Return of the Java Master");
        currentMovie.setReleaseDate(LocalDate.parse("2021-01-21"));

        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        String jsonBody = jsonb.toJson(currentMovie);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/webapi/movies")
                .then()
                .statusCode(201)
                .extract()
                .response();
        testDataResourceLocation = response.getHeader("location");
    }

    @Order(3)
    @Test
    void shouldFineOne() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        Movie existingMovie = jsonb.fromJson(jsonBody, Movie.class);
        assertNotNull(existingMovie);
        assertEquals("The Return of the Java Master", existingMovie.getTitle());
        assertEquals("Horror", existingMovie.getGenre());
        assertEquals(19.99, existingMovie.getPrice().doubleValue());
        assertEquals("NC-17", existingMovie.getRating());
        assertEquals(LocalDate.parse("2021-01-21").toString(), existingMovie.getReleaseDate().toString());
    }

    @Order(4)
    @Test
    void shouldUpdate() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        // http://json-b.net/docs/user-guide.html
        // Create a new Jsonb instance using the default JsonbBuilder implementation
        Jsonb jsonb = JsonbBuilder.create();
        Movie existingMovie = jsonb.fromJson(jsonBody, Movie.class);
        assertNotNull(existingMovie);
        existingMovie.setTitle("Ghostbusters 2016");
        existingMovie.setPrice(BigDecimal.valueOf(16.99));
        existingMovie.setReleaseDate(LocalDate.parse("2016-07-15"));

        String jsonRequestBody = jsonb.toJson(existingMovie);
        given()
                .contentType(ContentType.JSON)
                .body(jsonRequestBody)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

    @Order(5)
    @Test
    void shouldDelete() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(testDataResourceLocation)
                .then()
                .statusCode(204);
    }
}