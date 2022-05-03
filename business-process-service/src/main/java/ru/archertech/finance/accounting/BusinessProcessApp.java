package ru.archertech.finance.accounting;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class BusinessProcessApp {
    public static void main(String... args) {
        //noinspection resource
        SpringApplication.run(BusinessProcessApp.class, args);
    }
}
