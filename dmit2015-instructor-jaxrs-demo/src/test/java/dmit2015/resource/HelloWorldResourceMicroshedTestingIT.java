package dmit2015.resource;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicroShedTest
public class HelloWorldResourceMicroshedTestingIT {

    @Container
    public static ApplicationContainer app = new ApplicationContainer()
            .withAppContextRoot("/dmit2015-instructor-jaxrs-demo")
            .withReadinessPath("/dmit2015-instructor-jaxrs-demo/webapi/helloworld");

    @Test
    void helloWorldText() {
        Response response = given()
                .accept("text/plain")
                .when()
                .get("/webapi/helloworld")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        assertEquals("Hello World from JAX-RS!", jsonBody);
    }

    @Test
    void helloWorldHtml() {
        Response response = given()
                .accept("text/html")
                .when()
                .get("/webapi/helloworld")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        assertEquals("<p>Hello World from <strong>JAX-RS</strong>", jsonBody);
    }

    @Test
    void helloWorldJson() {
        Response response = given()
                .accept("application/json")
                .when()
                .get("/webapi/helloworld")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        assertEquals("{\"message\":\"Hello World from JAX-RS\"}", jsonBody);
    }
}