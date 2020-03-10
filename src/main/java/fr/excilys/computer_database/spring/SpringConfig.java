package fr.excilys.computer_database.spring;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = "fr.excilys.computer_database")
public class SpringConfig extends AbstractContextLoaderInitializer {
	
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfig.class);
		return rootContext;
	}
	
	/*
	@Value("${jdbc.url}")
	private String jdbcurl;

	@Value("${jdbc.driver}")
	private String jdbc;

	@Value("${jdbc.username}")
	private String jdbcusername;

	@Value("${jdbc.password}")
	private String jdbcpassword;
	
	@Autowired
	private Environment env;
	
	
	public void setEnv() {
		env.getProperty("jdbc.url");
	}
	
	private final DatabaseConfig dataConfig;
	
	public SpringConfig(DatabaseConfig dataConfig) {
        this.dataConfig = dataConfig;
    }
	
	@Bean
	public Computer myComputerBean() {
		return new Computer(dataConfig.computerDaoConfig());
	}
	
	@Bean
	public Company myCompanyBean() {
		return new Company(dataConfig.companyDaoConfig());
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	*/
	

	

}
