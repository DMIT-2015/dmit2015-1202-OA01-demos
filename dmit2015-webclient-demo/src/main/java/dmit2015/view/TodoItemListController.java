package dmit2015.view;

import dmit2015.webclient.TodoItem;
import dmit2015.webclient.TodoItemService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentTodoItemListController")
@ViewScoped
public class TodoItemListController implements Serializable {

    @Inject
    @RestClient
    private TodoItemService _todoItemService;

    @Getter
    private List<TodoItem> todoitemList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            todoitemList = _todoItemService.findAll();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}