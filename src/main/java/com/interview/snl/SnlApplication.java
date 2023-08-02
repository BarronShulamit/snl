package com.interview.snl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SnlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnlApplication.class, args);
    }

}
