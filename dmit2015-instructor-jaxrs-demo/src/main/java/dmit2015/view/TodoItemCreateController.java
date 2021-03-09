package dmit2015.view;

import dmit2015.entity.TodoItem;
import dmit2015.repository.TodoItemRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("currentTodoItemCreateController")
@RequestScoped
public class TodoItemCreateController {

    @Inject
    private TodoItemRepository _todoitemRepository;

    @Getter
    private TodoItem newTodoItem = new TodoItem();

    public String onCreate() {
        String nextPage = "";
        try {
            _todoitemRepository.add(newTodoItem);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}