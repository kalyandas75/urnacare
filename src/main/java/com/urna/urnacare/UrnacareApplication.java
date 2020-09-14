package com.urna.urnacare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UrnacareApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrnacareApplication.class, args);
	}

}

