package dmit2015.webclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.Scanner;

public class TodoItemConsoleApp {

    public static void main(String... args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RetrofitTodoItemClient webClient = retrofit.create(RetrofitTodoItemClient.class);

        Call<List<TodoItem>> findAllCall = webClient.findAllTodoItem();
        findAllCall.enqueue(new Callback<List<TodoItem>>() {
            @Override
            public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
                System.out.println("Successfully fetched the following TodoItem:");
                List<TodoItem> todoItems = response.body();
                todoItems.forEach(System.out::println);
            }

            @Override
            public void onFailure(Call<List<TodoItem>> call, Throwable throwable) {
                System.out.println("Fail to fetch all TodoItem");
            }
        });

        System.out.println("Enter the id of the TodoItem to search for: ");
        Scanner scanner = new Scanner(System.in);
        Long queryTodoItemId = scanner.nextLong();
        Call<TodoItem> findOneCall = webClient.findOneById(queryTodoItemId);
        findOneCall.enqueue(new Callback<TodoItem>() {
            @Override
            public void onResponse(Call<TodoItem> call, Response<TodoItem> response) {
                if (response.isSuccessful()) {
                    TodoItem singleResult = response.body();
                    System.out.println("Successfully found the following item: ");
                    System.out.println(singleResult);
                } else if (response.code() == 404) {
                    System.out.println("No result found for " + queryTodoItemId);
                } else {
                    System.out.println("Bad request");
                }

            }

            @Override
            public void onFailure(Call<TodoItem> call, Throwable throwable) {
                System.out.println("Fail to fetch on TodoItem");
            }
        });

//        System.out.println("Enter the name of a new TodoItem: ");
//        String name = scanner.next();
//        System.out.println("Is the new TodoItem complete?");

    }
}
