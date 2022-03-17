package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Job4jAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Job4jAuthApplication.class, args);
    }

}
