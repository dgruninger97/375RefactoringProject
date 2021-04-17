import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import RESTAPI.PlayerController;

@SpringBootApplication
public class RESTAPIRunner {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PlayerController.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		app.run(args);
	}
}
