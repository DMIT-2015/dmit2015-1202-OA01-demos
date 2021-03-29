package dmit2015.repository;

import dmit2015.entity.TodoItem;
import dmit2015.security.TodoItemSecurityInterceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
@Interceptors({TodoItemSecurityInterceptor.class})
public class TodoItemRepository {

    @Inject
    private SecurityContext _securityContext;

    @PersistenceContext
    private EntityManager em;

    public void add(TodoItem newTodoItem) {
        String username = _securityContext.getCallerPrincipal().getName();
        newTodoItem.setUsername(username);

        em.persist(newTodoItem);
    }

    public void update(TodoItem updatedTodoItem) {
        Optional<TodoItem> optionalTodoItem = findById(updatedTodoItem.getId());
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            existingTodoItem.setName(updatedTodoItem.getName());
            existingTodoItem.setComplete(updatedTodoItem.isComplete());
            em.merge(existingTodoItem);
            em.flush();
        }
    }

    public void remove(Long id) {
        Optional<TodoItem> optionalTodoItem = findById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            em.remove(existingTodoItem);
            em.flush();
        }
    }

    public Optional<TodoItem> findById(Long id) {
        Optional<TodoItem> optionalTodoItem = Optional.empty();
        try {
            TodoItem querySingleResult = em.find(TodoItem.class, id);
            if (querySingleResult != null) {
                optionalTodoItem = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalTodoItem;
    }

    public List<TodoItem> findAll() {
        List<TodoItem> queryResultList;
        // Return all TodoItem for the role ADMIN or Administration
        if (_securityContext.isCallerInRole("ADMIN") || _securityContext.isCallerInRole("Administration")) {
            queryResultList = em.createQuery(
                    "SELECT ti FROM TodoItem ti"
                    , TodoItem.class)
                    .getResultList();
        } else {
            // Return TodoItem associated with the current user
            String username = _securityContext.getCallerPrincipal().getName();
            queryResultList = em.createQuery(
                    "SELECT ti FROM TodoItem ti WHERE ti.username = :usernameValue "
                    , TodoItem.class)
                    .setParameter("usernameValue", username)
                    .getResultList();
        }
        return queryResultList;

    }

}

