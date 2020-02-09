package ru.agronom.springboot_mail_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import ru.agronom.springboot_mail_service.service.MailSendService;
import ru.agronom.springboot_mail_service.service.MessageGetService;

import java.util.Calendar;

@Controller

public class MailConsumer {


    private final MailSendService mailSendService;
    private final MessageGetService messageGetService;

    public MailConsumer(MailSendService mailSendService, MessageGetService messageGetService) {
        this.mailSendService = mailSendService;
        this.messageGetService = messageGetService;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void run() {
        messageGetService.getMail();


    }
}
