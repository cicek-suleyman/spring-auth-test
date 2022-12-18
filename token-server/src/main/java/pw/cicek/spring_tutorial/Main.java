package pw.cicek.spring_tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.logging.Logger;

@SpringBootApplication
public class Main {
	public final static String username = "root";
	public final static String password = "root";

	private static final Logger log = Logger.getLogger(Main.class.getName());
	private static ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Main.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);

		appContext = application.run();
	}
}
