package ru.agronom.springboot_mail_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;
import ru.agronom.springboot_mail_service.service.MailSendService;

import javax.annotation.Resource;

@Controller
public class MailSendController {
    @Resource
    private MailSendService mailSendService;
    @Resource(name = "messageQueueService")
    private IMessageQueue messageQueue;



    @Scheduled(initialDelay = 1000, fixedRate = 50)
    public void run(){
        while (messageQueue.isNext()){
            mailSendService.sendMessage(messageQueue.poll());
        }
    }
}
