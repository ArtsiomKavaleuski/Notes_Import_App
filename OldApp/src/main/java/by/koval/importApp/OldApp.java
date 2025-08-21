package by.koval.importApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OldApp {

	public static void main(String[] args) {
		SpringApplication.run(OldApp.class, args);
	}

}
