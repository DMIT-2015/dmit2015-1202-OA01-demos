package dmit2015.webclient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.Scanner;

public class TodoItemConsoleApp {

    public static void findTodoItemById(RetrofitTodoItemClient webClient, Long todoItemId) {
        Call<TodoItem> callFindOne = webClient.findOneById(todoItemId);
        callFindOne.enqueue(new Callback<TodoItem>() {
            @Override
            public void onResponse(Call<TodoItem> call, Response<TodoItem> response) {
                if (response.isSuccessful()) {
                    TodoItem singleTodoItem = response.body();
                    System.out.println("Found the following item: " + singleTodoItem);
                } else if (response.code() == 404) {
                    System.out.println("No matching item with an id of " + todoItemId);
                } else {
                    System.out.println("Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TodoItem> call, Throwable throwable) {
                System.out.println("Error connecting with service.");
            }
        });
    }
    public static void createTodoItem(RetrofitTodoItemClient webClient, String name) {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName(name);
        newTodoItem.setComplete(false);
        Call<ResponseBody> callCreate = webClient.create(newTodoItem);
        callCreate.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String resourceLocation = response.headers().get("location");
                    System.out.println("Successfully created resource at: " + resourceLocation);
                } else {
                    System.out.println("Request was not successful with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                System.out.println("Fail to communicate with web service.");
            }
        });
    }

    public static void fetchTodoItems(RetrofitTodoItemClient webClient) {
        Call<List<TodoItem>> callFindAll = webClient.findAllTodoItem();

        callFindAll.enqueue(new Callback<List<TodoItem>>() {
            @Override
            public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
                if (response.isSuccessful()) {
                    List<TodoItem> todoItems = response.body();
                    todoItems.forEach(System.out::println);
                } else {
                    System.out.println("Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<TodoItem>> call, Throwable throwable) {
                System.out.println("Connection error. Unable to call web service.");
            }
        });
    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/dmit2015-instructor-jaxrs-demo/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RetrofitTodoItemClient webClient = retrofit.create(RetrofitTodoItemClient.class);

        int menuChoice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("TodoItem Program Options");
            System.out.println("-------------------------");
            System.out.println("1. Fetch All TodoItem");
            System.out.println("2. Search for a single TodoItem");
            System.out.println("3. Create a new TodoItem");
            System.out.println("0. Exit program");
            System.out.println("Your choice >>> ");
            menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1: {
                    fetchTodoItems(webClient);
                }
                break;
                case 2: {
                    System.out.println("Enter the TodoItem id to search for: ");
                    Long queryId = scanner.nextLong();
                    findTodoItemById(webClient, queryId);
                }
                break;
                case 3: {
                    System.out.println("Enter the name of the TodoItem: ");
                    // Must consume the remaining newline after reading primitive value before reading a string input
                    scanner.nextLine();
                    String name = scanner.nextLine();   // next() reads a single word, nextLine() reads enter line
                    createTodoItem(webClient, name);
                }
                break;
                case 0:
                    System.out.println("Good-bye");
                    break;
                default:
                    System.out.println("Invalid input choice! You must enter a number between 0 - 3");
                    break;
            }
        } while (menuChoice != 0);


    }
}
