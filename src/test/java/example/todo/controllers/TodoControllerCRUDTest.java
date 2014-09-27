package example.todo.controllers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import example.todo.config.AngularTodoWebConfig;
import example.todo.config.TestWebApplicationContext;
import example.todo.dtos.TodoDTO;
import example.todo.models.Todo;
import example.todo.services.NotFoundException;
import example.todo.services.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import test.util.IntegrationTestUtil;

import javax.annotation.Resource;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by tatsuya on 2014/09/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {TestWebApplicationContext.class, AngularTodoWebConfig.class}
)
@WebAppConfiguration
@TestExecutionListeners({
        ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class TodoControllerCRUDTest {

    @Autowired
    public WebApplicationContext wac;

    @Resource
    private TodoService todoService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        //this.mockMvc = MockMvcBuilders.standaloneSetup(new TodoController(), new ApplicationExceptionHandler()).build();
    }

    @Test
    @DatabaseSetup("classpath:datasets/todos.xml")
    public void getField() throws Exception {
        mockMvc.perform(get("/todos/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is("user1")))
                .andExpect(jsonPath("$.description", is("this is test1")))
                .andExpect(jsonPath("$.dueDate", is("2014-01-01 00:00:00")));
    }

    @Test
    @DatabaseSetup("classpath:datasets/todos.xml")
    public void getFields() throws Exception {
        mockMvc.perform(get("/todos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author"      ,is("user1")))
                .andExpect(jsonPath("$[0].description" ,is("this is test1")))
                .andExpect(jsonPath("$[0].dueDate"     ,is("2014-01-01 00:00:00")))
                .andExpect(jsonPath("$[1].author"      ,is("user2")))
                .andExpect(jsonPath("$[1].description" ,is("this is test2")))
                .andExpect(jsonPath("$[1].dueDate"     ,is("2014-01-02 00:00:00")));
    }

    @Test
    @DatabaseSetup("classpath:datasets/empty_todos.xml")
    public void getFieldWhenTodoIsNotFound() throws Exception{
        mockMvc.perform(get("/todos/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Not found todo.\""));
    }

    @Test
    @DatabaseSetup("classpath:datasets/empty_todos.xml")
    public void createField() throws Exception {
        TodoDTO created = new TodoDTO();
        created.setAuthor("author");
        created.setDescription("this is test1");
        created.setDueDate(IntegrationTestUtil.buildDateFrom_yyyyMMddHHmmss("20140102000000"));

        mockMvc.perform(post("/todos")
               .contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8)
               .content(IntegrationTestUtil.convertObjectToJsonBytes(created)))
               .andExpect(status().isCreated());
    }

    @Test
    @DatabaseSetup("classpath:datasets/todos.xml")
    public void updateField() throws Exception {
        TodoDTO updated = new TodoDTO();
        updated.setAuthor("updated user1");
        updated.setDescription("updated test1");
        updated.setDueDate(IntegrationTestUtil.buildDateFrom_yyyyMMddHHmmss("20140102000000"));

        mockMvc.perform(put("/todos/1")
                .contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(updated)))
                .andExpect(status().isOk());

        Todo todo = todoService.findById(1);
        assertThat(todo.getAuthor()       ,is("updated user1"));
        assertThat(todo.getDescription()  ,is("updated test1"));
        assertEquals(IntegrationTestUtil.buildDateFrom_yyyyMMddHHmmss("20140102000000"), todo.getDueDate());

    }

    @Test
    @DatabaseSetup("classpath:datasets/empty_todos.xml")
    public void updateFieldWhenTodoIsNotFound() throws Exception {
        TodoDTO updated = new TodoDTO();
        updated.setAuthor("updated user1");
        updated.setDescription("updated test1");
        updated.setDueDate(IntegrationTestUtil.buildDateFrom_yyyyMMddHHmmss("20140102000000"));

        mockMvc.perform(put("/todos/1")
                .contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(updated)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DatabaseSetup("classpath:datasets/todos.xml")
    public void deleteField() throws Exception{
        mockMvc.perform(delete("/todos/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        try {
            todoService.findById(1);
        }catch(NotFoundException e) {
            assertEquals(e.getClass(), NotFoundException.class);
        }
    }

    @Test
    @DatabaseSetup("classpath:datasets/empty_todos.xml")
    public void deleteFieldWhtnTodoIsNotFound() throws Exception {
        mockMvc.perform(delete("/todos/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
