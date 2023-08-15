package main.java.ru.tech.sensorOverDAO.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@ComponentScan
@Configuration
@PropertySource("classpath:db.properties")
public class SensorOverDAOConfiguration {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SensorOverDAOConfiguration(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
        driverManagerDataSource.setUrl(environment.getRequiredProperty("url"));
        driverManagerDataSource.setUsername(environment.getProperty("username_value"));
        driverManagerDataSource.setPassword(environment.getProperty("password"));

        return  driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){return new JdbcTemplate(dataSource());}

    @Bean
    public ModelMapper modelMapper(){return  new ModelMapper();}
}
