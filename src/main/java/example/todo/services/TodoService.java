package example.todo.services;

import example.todo.dtos.TodoDTO;
import example.todo.models.Todo;

import java.util.List;

/**
 * Created by tatsuya on 2014/09/20.
 */
public interface TodoService {

    Todo create(TodoDTO created);

    Todo delete(long id) throws NotFoundException;

    List<Todo> findAll();

    Todo findById(long id) throws NotFoundException;

    Todo update(TodoDTO updated) throws NotFoundException;
}
