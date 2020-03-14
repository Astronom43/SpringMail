package ru.agronom.springboot_mail_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

public class SpringbootMailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMailServiceApplication.class, args);
    }
}