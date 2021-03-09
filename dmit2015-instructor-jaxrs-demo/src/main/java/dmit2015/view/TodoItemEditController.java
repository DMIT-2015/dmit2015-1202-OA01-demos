package dmit2015.view;

import dmit2015.entity.TodoItem;
import dmit2015.repository.TodoItemRepository;
import lombok.Getter;
import lombok.Setter;
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
    private TodoItemRepository _todoitemRepository;

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
            Optional<TodoItem> optionalEntity = _todoitemRepository.findById(editId);
            optionalEntity.ifPresent(entity -> existingTodoItem = entity);
        }
    }

    public String onUpdate() {
        String nextPage = "";
        try {
            _todoitemRepository.update(existingTodoItem);
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
            _todoitemRepository.remove(existingTodoItem.getId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}