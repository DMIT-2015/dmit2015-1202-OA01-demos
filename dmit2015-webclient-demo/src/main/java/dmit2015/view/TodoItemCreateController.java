package dmit2015.view;

import dmit2015.webclient.TodoItem;
import dmit2015.webclient.TodoItemService;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("currentTodoItemCreateController")
@RequestScoped
public class TodoItemCreateController {

    @Inject
    @RestClient
    private TodoItemService _todoItemService;

    @Getter
    private TodoItem newTodoItem = new TodoItem();

    public String onCreate() {
        String nextPage = "";
        try {
            _todoItemService.create(newTodoItem);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}