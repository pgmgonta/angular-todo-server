package example.todo.repositories;

import example.todo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tatsuya on 2014/09/20.
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
