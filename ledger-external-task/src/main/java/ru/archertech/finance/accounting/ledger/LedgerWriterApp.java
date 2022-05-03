package ru.archertech.finance.accounting.ledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LedgerWriterApp {

    public static void main(String... args) {
        //noinspection resource
        SpringApplication.run(LedgerWriterApp.class, args);
    }

}
