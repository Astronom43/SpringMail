package ru.agronom.springboot_mail_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.service.MailSendService;

@RestController
public class MessageController {
    private final MailSendService sendService;

    public MessageController(MailSendService sendService) {
        this.sendService = sendService;
    }

    @PostMapping("/mail")
    public String sendMessage(@RequestBody Message message) {
        sendService.sendMessage(message);
        return "OK";
    }

}
