package newCar.socket_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SocketAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketAppApplication.class, args);
	}

}
