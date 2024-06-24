package com.tasktide.calendarServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
@EnableMethodSecurity
public class CalendarServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarServicesApplication.class, args);
    }

}
