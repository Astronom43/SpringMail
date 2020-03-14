package ru.agronom.springboot_mail_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;

import javax.annotation.Resource;
import javax.validation.ValidationException;

@Service
@RabbitListener(queues = "mail")
public class MailRecService {
    @Resource(name = "messageValidateService")
    private MessageValidateService validateService;

    @Resource(name = "mailSendService")
    private MailSendService mailSendService;

    @RabbitHandler
    public void receiveMessage(Message message) {

        try{
            validateService.isValid(message);
            mailSendService.sendMessage(message);

        } catch (ValidationException e){

        } catch (MailException e){

        }
    }

}
