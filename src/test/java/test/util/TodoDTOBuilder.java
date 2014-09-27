package test.util;

import example.todo.dtos.TodoDTO;

import java.util.Date;

/**
 * Created by tatsuya on 2014/09/25.
 */
public final class TodoDTOBuilder {

    private TodoDTO todoDTO;

    public TodoDTOBuilder() {
        todoDTO = new TodoDTO();
    }

    public TodoDTOBuilder author(String author) {
        todoDTO.setAuthor(author);
        return this;
    }

    public TodoDTOBuilder description(String description) {
        todoDTO.setDescription(description);
        return this;
    }

    public TodoDTOBuilder dueDate(Date dueDate) {
        todoDTO.setDueDate(dueDate);
        return this;
    }

    public TodoDTO build() {
        return todoDTO;
    }
}
