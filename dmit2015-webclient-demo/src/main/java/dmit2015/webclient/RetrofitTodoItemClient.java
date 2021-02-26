package dmit2015.webclient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitTodoItemClient {

    @GET("/dmit2015-instructor-jaxrs-demo/webapi/TodoItems")
    Call<List<TodoItem>> findAllTodoItem();

    @GET("/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/{id}")
    Call<TodoItem> findOneById(@Path("id") Long id);

    @POST("/dmit2015-instructor-jaxrs-demo/webapi/TodoItems")
    Call<ResponseBody> create(@Body TodoItem newTodoItem);

    @PUT("/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/{id}")
    Call<ResponseBody> update(@Path("id") Long id, @Body TodoItem updatedTodoItem);

    @DELETE("/dmit2015-instructor-jaxrs-demo/webapi/TodoItems/{id}")
    Call<ResponseBody> delete(@Path("id") Long id);

}
