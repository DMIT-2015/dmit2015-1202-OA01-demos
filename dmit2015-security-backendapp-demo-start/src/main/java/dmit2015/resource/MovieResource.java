/**
 *
 * curl -v -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies
 *
 * curl -v -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/3
 *
 curl -i -X POST http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies \
 -d '{"genre":"Horror","price":29.99,"rating":"PG","releaseDate":"2021-01-01","title":"COVID19 Horror Stories"}' \
 -H 'Content-Type:application/json'

 curl -v -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5

curl -i -X PUT http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5 \
 -d '{"id":5, "genre":"Horror","price":19.99,"rating":"PG","releaseDate":"2021-01-01","title":"COVID19 Happy Stories"}' \
 -H 'Content-Type:application/json'

 curl -v -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5

 curl -v -X DELETE http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5

 curl -v -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5

 */
package dmit2015.resource;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("movies")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieRepository _movieRepository;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response listAll() {
        List<Movie> movies = _movieRepository.findAll();
        return Response.ok(movies).build();
    }

    @GET
    @Path("{id : \\d+}")
    public Response findOneById(@PathParam("id") Long movieId) {
        if (movieId == null) {
            throw new BadRequestException();
        }

        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            return Response.ok(existingMovie).build();
        } else {
            throw new NotFoundException();
        }
    }

    @POST
    public Response create(@Valid Movie newMovie) {
        if (newMovie == null) {
            throw new BadRequestException();
        }

        _movieRepository.add(newMovie);
        URI locationUri = uriInfo.getAbsolutePathBuilder().path(newMovie.getId().toString()).build();

        return Response.created(locationUri).build();
    }

    @PUT
    @Path("{id : \\d+}")
    public Response update(@PathParam("id") Long movieId, @Valid Movie updatedMovie) {
        if (movieId == null || updatedMovie.getId() == null || !movieId.equals(updatedMovie.getId())) {
            throw new BadRequestException();
        }

        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        if (optionalMovie.isEmpty()) {
            throw new NotFoundException();
        }
        _movieRepository.update(updatedMovie);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id : \\d+}")
    public Response delete(@PathParam("id") Long movieId) {
        if (movieId == null) {
            throw new BadRequestException();
        }

        _movieRepository.remove(movieId);

        return Response.noContent().build();
    }
}
