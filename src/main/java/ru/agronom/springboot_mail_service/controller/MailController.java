package ru.agronom.springboot_mail_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.agronom.springboot_mail_service.domain.Message;


import java.util.ArrayList;
import java.util.Iterator;

@Controller
public class MailController {
    private Message[] messageList = new Message[]{new Message("Sergshubin@yandex.ru", "bofisin368@mailmink.com", "subj", "test"),
            new Message("Sergshubin@yandex.ru", "bofisin368@mailmink.com", "subj1", "test1")};
    @GetMapping("/mails")
    @ResponseBody
    public Message[] getAll(){
        return messageList;
    }


}
