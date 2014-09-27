package example.todo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by tatsuya on 2014/09/20.
 */
public final class TodoDTO {

    private long id;

    @NotEmpty
    @NotNull
    @Size(min=1, max=256, message="todo.validation.error.author.size")
    private String author;

    @NotEmpty
    @NotNull
    @Size(min=1, max=512, message="todo.validation.error.description.size")
    private String description;

    @NotNull
    private Date   dueDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
