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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test.util.IntegrationTestUtil;
import test.util.TodoDTOBuilder;

import javax.annotation.Resource;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class TodoControllerValidateTest {

    @Autowired
    public WebApplicationContext wac;

    @Resource
    private TodoService todoService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void createFieldWithEmptyAuthor() throws Exception {
        postTodoAndExpectToReturnBadRequest(getTodoWithEmptyAuthor());
    }

    @Test
    public void createFieldWithEmptyDescription() throws Exception {
        postTodoAndExpectToReturnBadRequest(getTodoWithEmptyDescription());
    }

    @Test
    public void updateFieldWithEmptyAuthor() throws Exception {
        postTodoAndExpectToReturnBadRequest(getTodoWithEmptyAuthor());
    }

    @Test
    public void updateFieldWithEmptyDescription() throws Exception {
        postTodoAndExpectToReturnBadRequest(getTodoWithEmptyDescription());
    }

    private void postTodoAndExpectToReturnBadRequest(TodoDTO created) throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(created)))
                .andExpect(status().isBadRequest());
    }

    private void putTodoAndExpectToReturnBadRequest(TodoDTO updated) throws Exception {
        mockMvc.perform(put("/todos")
                .contentType(IntegrationTestUtil.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(updated)))
                .andExpect(status().isBadRequest());
    }

    private TodoDTO getTodoWithEmptyAuthor() throws Exception {
        TodoDTO dto = new TodoDTOBuilder().author("")
                .description("this is test1")
                .dueDate(IntegrationTestUtil.buildDateFrom_yyyyMMddHHmmss("20140101000000"))
                .build();
        return dto;
    }

    private TodoDTO getTodoWithEmptyDescription() throws Exception {
        TodoDTO dto = new TodoDTOBuilder().author("author1")
                .description("")
                .dueDate(IntegrationTestUtil.buildDateFrom_yyyyMMddHHmmss("20140101000000"))
                .build();
        return dto;
    }

}
