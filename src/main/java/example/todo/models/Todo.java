package example.todo.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tatsuya on 2014/09/20.
 */
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "author", length = 256, nullable = false)
    private String author;

    @Column(name = "description", length = 512, nullable = false)
    private String description;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

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
