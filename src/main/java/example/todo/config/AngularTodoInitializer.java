package example.todo.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by tatsuya on 2014/09/19.
 */
public class AngularTodoInitializer implements WebApplicationInitializer {

    private static final String SERVLET_NAME = "angular-todo-example";
    private static final String REST_MAPPING_PATH = "/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(AngularTodoContext.class, AngularTodoWebConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic reg = servletContext.addServlet(SERVLET_NAME, dispatcherServlet);
        reg.setLoadOnStartup(1);
        reg.addMapping(REST_MAPPING_PATH);
    }
}
