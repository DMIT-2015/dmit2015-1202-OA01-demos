package dmit2015.view;

import dmit2015.entity.TodoItem;
import dmit2015.repository.TodoItemRepository;
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
    private TodoItemRepository _todoitemRepository;

    @Getter
    private List<TodoItem> todoitemList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            todoitemList = _todoitemRepository.findAll();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}