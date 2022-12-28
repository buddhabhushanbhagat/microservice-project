package com.remitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectRemitterMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectRemitterMicroApplication.class, args);
	}

}
