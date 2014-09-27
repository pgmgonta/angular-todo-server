package example.todo.services;

import example.todo.dtos.TodoDTO;
import example.todo.models.Todo;
import example.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * Created by tatsuya on 2014/09/20.
 */
@Service
public class TodoRepositoryService implements TodoService {

    private static final String TODO_NOT_FOUND_MESSAGE = "todo.notfound.message";

    @Resource
    TodoRepository todoRepository;

    @Resource
    MessageSource messageSource;

    @Override
    @Transactional
    public Todo create(TodoDTO created) {
        Todo t = new Todo();
        t.setAuthor(created.getAuthor());
        t.setDescription(created.getDescription());
        t.setDueDate(created.getDueDate());
        return todoRepository.save(t);
    }

    @Override
    @Transactional(noRollbackFor = NotFoundException.class)
    public Todo delete(long id) throws NotFoundException {
        Todo t = todoRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException(TODO_NOT_FOUND_MESSAGE);
        }
        todoRepository.delete(t);
        return t;
    }

    @Override
    @Transactional(readOnly = true)
    public Todo findById(long id) throws NotFoundException {
        Todo t = todoRepository.findOne(id);
        if (t == null) {
            throw new NotFoundException(TODO_NOT_FOUND_MESSAGE);
        }
        return t;
    }

    @Override
    @Transactional(rollbackFor = NotFoundException.class)
    public Todo update(TodoDTO updated) throws NotFoundException {
        Todo t = todoRepository.findOne(updated.getId());
        if (t == null) {
            throw new NotFoundException(TODO_NOT_FOUND_MESSAGE);
        }
        t.setAuthor(updated.getAuthor());
        t.setDescription(updated.getDescription());
        t.setDueDate(updated.getDueDate());
        return todoRepository.save(t);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    private String getMessage(String message, Object... args) {
        Locale current = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, args, current);
    }


}
