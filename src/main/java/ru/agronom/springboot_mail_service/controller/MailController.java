package ru.agronom.springboot_mail_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agronom.springboot_mail_service.domain.Message;

@RestController
public class MailController {

    private Message[] messageList = new Message[]{
            new Message("Sergshubin@yandex.ru",  "subj", "test"),
            new Message("Sergshubin@yandex.ru",  "subj1", "test1")
    };

    @GetMapping("/mails")
    public Message[] getAll() {
        return messageList;
    }
}
