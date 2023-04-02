package com.spring.foodsearchapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class FoodSearchAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodSearchAppApplication.class, args);
    }

}
