package com.tasktide.taskServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServicesApplication.class, args);
	}

}
