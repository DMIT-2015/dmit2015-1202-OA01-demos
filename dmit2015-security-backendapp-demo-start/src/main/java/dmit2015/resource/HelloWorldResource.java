/**
 * curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/helloworld -H 'Accept: text/plain'
 *
 * curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/helloworld -H 'Accept: text/html'
 *
 * curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/helloworld -H 'Accept: application/json'
 *
 */
package dmit2015.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/helloworld")
@ApplicationScoped // or @RequestedScoped
public class HelloWorldResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN) //@Produces("text/plain")
    public Response helloWorldText() {
        String message = "Hello World from JAX-RS!";
        return Response.ok(message).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML) // "text/html"
    public Response helloWorldHtml() {
        String message = "<p>Hello World from <strong>JAX-RS</strong>";
        return Response.ok(message).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)   // "application/json"
    public Response helloWorldJson() {
        String message = "{\"message\":\"Hello World from JAX-RS\"}";
        return Response.ok(message).build();
    }
}
