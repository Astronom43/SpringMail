package ru.agronom.springboot_mail_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

@SpringBootApplication

public class SpringbootMailServiceApplication {
    Logger logger = LoggerFactory.getLogger(SpringbootMailServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMailServiceApplication.class, args);
    }




}
