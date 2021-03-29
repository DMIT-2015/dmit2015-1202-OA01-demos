package dmit2015.resource;

import dmit2015.config.JAXRSConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class HelloWorldResourceArquillianIT {

	@Deployment
    public static WebArchive createDeployment() throws IOException, XmlPullParserException {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        // Make sure the archiveName matches the maven finalName in your pom.xml file
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        final String archiveName = model.getArtifactId() + ".war";
        return ShrinkWrap.create(WebArchive.class,archiveName)
                .addClasses(JAXRSConfiguration.class, HelloWorldResource.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Test
    @RunAsClient
    public void checkClientSiteIsUp() {
        given().when().get("https://www.nait.ca/").then().statusCode(200);
        given().when().get("http://localhost:8080/").then().statusCode(200);
        given().when().get("http://localhost:8080/dmit2015-instructor-jaxrs-demo/").then().statusCode(403);
    }

    @Test
    @RunAsClient
    void helloWorldText() {
        Response response = given()
                .urlEncodingEnabled(false)
                .accept("text/plain")
                .when()
                .get("/dmit2015-instructor-jaxrs-demo/webapi/helloworld")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        assertEquals("Hello World from JAX-RS!", jsonBody);
    }

    @Test
    @RunAsClient
    void helloWorldHtml() {
        Response response = given()
                .accept("text/html")
                .when()
                .get("/dmit2015-instructor-jaxrs-demo/webapi/helloworld")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        assertEquals("<p>Hello World from <strong>JAX-RS</strong>", jsonBody);
    }

    @Test
    @RunAsClient
    void helloWorldJson() {
        Response response = given()
                .accept("application/json")
                .when()
                .get("/dmit2015-instructor-jaxrs-demo/webapi/helloworld")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();
        assertEquals("{\"message\":\"Hello World from JAX-RS\"}", jsonBody);
    }
}