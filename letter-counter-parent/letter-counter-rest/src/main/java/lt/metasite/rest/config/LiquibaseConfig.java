package lt.metasite.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfig {

	@Autowired
	private Environment env;

	@Autowired
	private PersistenceConfig persistenceConfiguration;

	@Bean
	public SpringLiquibase liquibase() {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(persistenceConfiguration.dataSource());
		liquibase.setChangeLog(env.getProperty("spring.liquibase.change-log"));
		return liquibase;
	}
}
