package lt.metasite.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { 
		"lt.metasite.rest", 
		"lt.metasite.bl.dao", 
		"lt.metasite.bl.helper" 
})
public class Application extends SpringBootServletInitializer {

	private static final Class<Application> APLICATION_CLASS = Application.class;

	public static void main(String[] args) {
		SpringApplication.run(APLICATION_CLASS, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(APLICATION_CLASS);
	}

}
