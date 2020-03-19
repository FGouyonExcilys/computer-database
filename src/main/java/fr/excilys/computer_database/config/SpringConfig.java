package fr.excilys.computer_database.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = {"fr.excilys.computer_database.dao","fr.excilys.computer_database.service",
							   "fr.excilys.computer_database.mapper", "fr.excilys.computer_database.controllers" })
@PropertySource("classpath:datasource.properties")
public class SpringConfig implements WebMvcConfigurer, WebApplicationInitializer {
	
	@Autowired
	private Environment environment;
	
	@Bean
	NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		return namedParameterJdbcTemplate;
	}
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("dataSource.jdbc.driver"));
        dataSource.setUrl(environment.getRequiredProperty("dataSource.jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("dataSource.jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("dataSource.jdbc.password"));
        return dataSource;
    }
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(SpringConfig.class,SpringMvcConfig.class);
		webContext.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dynamicServlet", new DispatcherServlet(webContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}

}
