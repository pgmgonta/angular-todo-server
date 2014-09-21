package example.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


/**
 * Created by tatsuya on 2014/09/21.
 */

@Configuration
@EnableJpaRepositories(basePackages = "example.todo.repositories")
@ComponentScan({"example.todo.controllers", "example.todo.services"})
@EnableWebMvc
public class TestWebApplicationContext {

    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String JDBC_DB_URL            = "jdbc:mysql://localhost:3306/angular_todo_development";
    private static final String JDBC_DB_USER           = "todo";
    private static final String JDBC_DB_PASSWORD       = "s3cret";

    private static final String CONTROLLER_PACKAGE     = "example.todo.controllers";
    private static final String MODEL_PACKAGE          = "example.todo.models";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds =  new DriverManagerDataSource();
        ds.setDriverClassName(JDBC_DRIVER_CLASS_NAME);
        ds.setUrl(JDBC_DB_URL);
        ds.setUsername(JDBC_DB_USER);
        ds.setPassword(JDBC_DB_PASSWORD);
        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(CONTROLLER_PACKAGE, MODEL_PACKAGE);
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(CONTROLLER_PACKAGE, MODEL_PACKAGE);
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

}
