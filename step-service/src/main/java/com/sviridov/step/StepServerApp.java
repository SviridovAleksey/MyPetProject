package com.sviridov.step;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StepServerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StepServerApp.class, args);
    }

}
