package com.sviridov.levels;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LevelsServerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LevelsServerApp.class, args);
    }

}
