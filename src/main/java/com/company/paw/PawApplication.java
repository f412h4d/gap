package com.company.paw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Date;

@SpringBootApplication
@EnableMongoAuditing
public class PawApplication {
    public static void main(String[] args) {
        SpringApplication.run(PawApplication.class, args);
        Date date = new Date();
        System.out.println(date);
    }
}
