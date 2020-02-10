package fr.cooptalent.neodrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NeodriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeodriveApplication.class, args);
	}

}

