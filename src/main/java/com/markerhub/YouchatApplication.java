package com.markerhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YouchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouchatApplication.class, args);

        System.out.println("http://localhost:8080");
    }

}
