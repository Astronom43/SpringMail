package ru.agronom.springboot_mail_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.agronom.springboot_mail_service.service.MessageGetService;

import java.util.Calendar;

@Controller
@EnableScheduling
public class MailConsumer {
    Logger logger = LoggerFactory.getLogger(MailConsumer.class);

    public MailConsumer(MessageGetService messageGetService) {
        this.messageGetService = messageGetService;
    }

    private final MessageGetService messageGetService;

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void run(){
        messageGetService.getMail();
    }
}
