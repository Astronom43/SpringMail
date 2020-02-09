package ru.agronom.springboot_mail_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.service.MailSendService;
import ru.agronom.springboot_mail_service.service.MessageGetService;

import java.util.Calendar;

@Service

public class MailConsumer {


    private final MessageGetService messageGetService;

    public MailConsumer(MessageGetService messageGetService) {
        this.messageGetService = messageGetService;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void run() {
        messageGetService.getMail();


    }
}
