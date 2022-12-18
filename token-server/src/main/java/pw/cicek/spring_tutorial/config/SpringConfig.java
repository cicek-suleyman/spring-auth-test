package pw.cicek.spring_tutorial.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootConfiguration
public class SpringConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
		dataSource.setUsername("auth_db");
		dataSource.setPassword("3LEnDMlXEVr6b8a9pho=");
		dataSource.setUrl("jdbc:mysql://localhost:3306/auth_test_db?serverTimezone=UTC");

		return dataSource;
	}
}
