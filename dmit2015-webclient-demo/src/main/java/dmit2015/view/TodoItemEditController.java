package dmit2015.view;

import dmit2015.webclient.TodoItem;
import dmit2015.webclient.TodoItemService;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentTodoItemEditController")
@ViewScoped
public class TodoItemEditController implements Serializable {

    @Inject
    @RestClient
    private TodoItemService _todoItemService;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private TodoItem existingTodoItem;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
//            Optional<TodoItem> optionalEntity = _todoitemRepository.findOneById(editId);
//            optionalEntity.ifPresent(entity -> existingTodoItem = entity);
            existingTodoItem = _todoItemService.findOneById(editId);
        }
    }

    public String onUpdate() {
        String nextPage = "";
        try {
//            _todoitemRepository.update(existingTodoItem);
            _todoItemService.update(editId, existingTodoItem);

            Messages.addFlashGlobalInfo("Update was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Update was not successful.");
        }
        return nextPage;
    }

    public String onDelete() {
        String nextPage = "";
        try {
//            _todoitemRepository.delete(existingTodoItem.getId());
            _todoItemService.delete(editId);

            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}