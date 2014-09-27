package example.todo.controllers;

import example.todo.dtos.TodoDTO;
import example.todo.models.Todo;
import example.todo.services.NotFoundException;
import example.todo.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tatsuya on 2014/09/20.
 */
@RestController
@RequestMapping("todos")
public class TodoController {
    @Resource
    TodoService todoService;

    //get one
    @RequestMapping(value = "{typeId}", method = RequestMethod.GET)
    public TodoDTO get(@PathVariable("typeId") long typeId) throws NotFoundException {
        return createDTO(todoService.findById(typeId));
    }

    //get all
    @RequestMapping(method = RequestMethod.GET)
    public List<TodoDTO> index() {
        return createDTOList(todoService.findAll());
    }

    //create
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDTO create(@Valid @RequestBody TodoDTO created) {
        return createDTO(todoService.create(created));
    }

    //update
    @RequestMapping(value = "{typeId}", method = RequestMethod.PUT)
    public TodoDTO update(@Valid @RequestBody TodoDTO updated, @PathVariable long typeId) throws NotFoundException {
        updated.setId(typeId);
        return createDTO(todoService.update(updated));
    }

    //delete
    @RequestMapping(value = "{typeId}", method = RequestMethod.DELETE)
    public TodoDTO delete(@PathVariable long typeId) throws NotFoundException {
        return createDTO(todoService.delete(typeId));
    }

    private TodoDTO createDTO(Todo entity) {
        TodoDTO dto = new TodoDTO();
        dto.setId(entity.getId());
        dto.setAuthor(entity.getAuthor());
        dto.setDescription(entity.getDescription());
        dto.setDueDate(entity.getDueDate());
        return dto;
    }

    private List<TodoDTO> createDTOList(List<Todo> entities) {
        ArrayList<TodoDTO> todos = new ArrayList<TodoDTO>();
        for (Todo t : entities) {
            todos.add(createDTO(t));
        }
        return todos;
    }

}
