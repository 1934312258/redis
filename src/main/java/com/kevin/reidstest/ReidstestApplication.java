package com.kevin.reidstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ReidstestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReidstestApplication.class, args);
    }

}
