package dmit2015.resource;

import dmit2015.entity.TodoItem;
import dmit2015.repository.TodoItemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;


/**
 * * Web API with CRUD methods for managing TodoItem.
 *
 *  URI						    Http Method     Request Body		                        Description
 * 	----------------------      -----------		------------------------------------------- ------------------------------------------
 *	/webapi/TodoItems			POST			{"name":"Demo DMIT2015 assignment 1",       Create a new TodoItem
 *                                         	    "complete":false}
 * 	/webapi/TodoItems/{id}		GET			                                                Find one TodoItem with a id value
 * 	/webapi/TodoItems		    GET			                                                Find all TodoItem
 * 	/webapi/TodoItems/{id}      PUT             {"id":5,                                    Update the TodoItem
 * 	                                            "name":"Submitted DMIT2015 assignment 7",
 *                                              "complete":true}
 * 	/webapi/TodoItems/{id}		DELETE			                                            Remove the TodoItem
 *

 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems

 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/1

 curl -i -X POST http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems \
 -d '{"name":"Finish DMIT2015 Assignment 1","complete":false}' \
 -H 'Content-Type:application/json'

 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/4

 curl -i -X PUT http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/4 \
    -d '{"id":4,"name":"Demo DMIT2015 Assignment 1","complete":true}' \
    -H 'Content-Type:application/json'

 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/4

 curl -i -X DELETE http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/4

 curl -i -X GET http://localhost:8080/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/4

 *
 */

@ApplicationScoped
// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("TodoItems")	        // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class TodoItemResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private TodoItemRepository todoItemRepository;

    @POST   // POST: /webapi/TodoItems
    public Response postTodoItem(@Valid TodoItem newTodoItem) {
        if (newTodoItem == null) {
            throw new BadRequestException();
        }
        todoItemRepository.add(newTodoItem);
        URI todoItemsUri = uriInfo.getAbsolutePathBuilder().path(newTodoItem.getId().toString()).build();
        return Response.created(todoItemsUri).build();
    }

    @GET    // GET: /webapi/TodoItems/5
    @Path("{id}")
    public Response getTodoItem(@PathParam("id") Long id) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        TodoItem existingTodoItem = optionalTodoItem.get();

        return Response.ok(existingTodoItem).build();
    }

    @GET    // GET: /webapi/TodoItems
    public Response getTodoItems() {
        return Response.ok(todoItemRepository.findAll()).build();
    }

    @PUT    // PUT: /webapi/TodoItems/5
    @Path("{id}")
    public Response updateTodoItem(@PathParam("id") Long id, @Valid TodoItem updatedTodoItem) {
        if (!id.equals(updatedTodoItem.getId())) {
            throw new BadRequestException();
        }

        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        todoItemRepository.update(updatedTodoItem);

        return Response.noContent().build();
    }

    @DELETE // DELETE: /webapi/TodoItems/5
    @Path("{id}")
    public Response deleteTodoItem(@PathParam("id") Long id) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }

        todoItemRepository.remove(id);

        return Response.noContent().build();
    }

}

