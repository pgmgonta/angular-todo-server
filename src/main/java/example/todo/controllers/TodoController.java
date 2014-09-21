package example.todo.controllers;

import example.todo.dtos.TodoDTO;
import example.todo.models.Todo;
import example.todo.services.NotFoundException;
import example.todo.services.TodoRepositoryService;
import example.todo.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.AbstractCollection;
import java.util.List;

/**
 * Created by tatsuya on 2014/09/20.
 */
@RestController
@RequestMapping("todos")
public class TodoController extends ApplicationController {
    @Resource
    TodoService todoService;

    //get one
    @RequestMapping(value = "/{typeId}", method = RequestMethod.GET)
    public Todo get(@PathVariable("typeId") long typeId) throws NotFoundException {
        return todoService.findById(typeId);
    }

    //get all
    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> index() {
        return todoService.findAll();
    }

    //create
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody TodoDTO created) throws Exception {
        return todoService.create(created);
    }

    //update
    @RequestMapping(value = "/{typeId}", method = RequestMethod.PUT)
    public Todo update(@Valid @RequestBody TodoDTO updated, @PathVariable long typeId) throws NotFoundException {
        updated.setId(typeId);
        return todoService.update(updated);
    }

    //delete
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public Todo delete(@PathVariable long typeId) throws NotFoundException {
        return todoService.delete(typeId);
    }
}
