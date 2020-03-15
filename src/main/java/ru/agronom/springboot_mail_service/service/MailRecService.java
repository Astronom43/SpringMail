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
import java.util.Optional;

@Service
@RabbitListener(queues = "mail")
public class MailRecService {
    @Resource(name = "messageValidateService")
    private MessageValidateService validateService;

    @Resource(name = "mailSendService")
    private MailSendService mailSendService;

    @RabbitHandler
    public String receiveMessage(Message message) {

        try{
            validateService.isValid(message);
            mailSendService.sendMessage(message);
            return "202";

        } catch (ValidationException e){
            return "400 "+e.getMessage();

        } catch (MailException e){
            return "500 "+e.getMessage();
        }
    }

}
