package com.tasktide.projectServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
@EnableMethodSecurity
public class ProjectServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectServicesApplication.class, args);
    }

}
