package example.todo.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by tatsuya on 2014/09/21.
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class AngularTodoWebConfig extends WebMvcConfigurerAdapter {

    private static final String JACKSON_OBJECTMAPPER_TIMEZONE = "jackson.objectmapper.timezone";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Resource
    Environment env;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.getObjectMapper().setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        converter.getObjectMapper().setTimeZone(TimeZone.getTimeZone(env.getProperty(JACKSON_OBJECTMAPPER_TIMEZONE)));
        converters.add(converter);
    }
}
