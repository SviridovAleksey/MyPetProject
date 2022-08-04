package ru.sviridov.rememberka.EurekaRememberka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaRememberkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaRememberkaApplication.class, args);
    }
}
