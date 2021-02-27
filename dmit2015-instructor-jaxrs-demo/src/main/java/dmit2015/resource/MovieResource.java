/**
 * read all movies
 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies
 * read a single movie
 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/3
 * create a new Movie
 curl -i -X POST http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies \
  -d '{"genre":"Horror","price":99.99,"rating":"PG","releaseDate":"2021-01-06","title":"Insurrection at Capital"}' \
  -H 'Content-Type: application/json'
 * update the Movie we just created
 curl -i -X PUT http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5 \
 -d '{"id":5, "genre":"Comedy","price":8.88,"rating":"PG","releaseDate":"2021-01-06","title":"Party at Capital"}' \
 -H 'Content-Type: application/json'
 * delete the Movie we created
 curl -i -X DELETE http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/movies/5
 *
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

    @GET
    public Response findAllMovie() {
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
        if (optionalMovie.isEmpty()) {
            throw new NotFoundException();
        }
        Movie existingMovie = optionalMovie.get();
        return Response.ok(existingMovie).build();
    }

    @POST
    public Response createNewMovie(@Valid Movie newMovie, @Context UriInfo uriInfo) {
        if (newMovie == null) {
            throw new BadRequestException();
        }

        _movieRepository.add(newMovie);
        URI locationUri = uriInfo.getAbsolutePathBuilder().path(newMovie.getId().toString()).build();
        return Response.created(locationUri).build();
    }

    @PUT
    @Path("{id : \\d+}")
    public Response updateMovie(@PathParam("id") Long movieId, @Valid Movie updatedMovie) {
        if (movieId == null || updatedMovie.getId() == null || !updatedMovie.getId().equals(movieId)) {
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
    public Response deleteMovie(@PathParam("id") Long movieId) {
        if (movieId == null) {
            throw new BadRequestException();
        }
        Optional<Movie> existingMovie = _movieRepository.findById(movieId);
        if (existingMovie.isEmpty()) {
            throw new NotFoundException();
        }
        _movieRepository.remove(movieId);
        return Response.noContent().build();

    }
}
