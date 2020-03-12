package fr.excilys.computer_database.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = {"fr.excilys.computer_database.dao","fr.excilys.computer_database.service",
							   "fr.excilys.computer_database.mapper", "fr.excilys.computer_database.servlets" })
@PropertySource("classpath:datasource.properties")
public class SpringConfig extends AbstractContextLoaderInitializer {
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfig.class);
		return rootContext;
	}
	
	@Bean
	NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		return namedParameterJdbcTemplate;
	}
	
	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false");
		driverManagerDataSource.setUsername("admincdb");
		driverManagerDataSource.setPassword("qwerty1234");
		driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return driverManagerDataSource;
	}

}
