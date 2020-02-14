package ru.agronom.springboot_mail_service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
