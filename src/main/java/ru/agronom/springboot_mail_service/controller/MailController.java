package ru.agronom.springboot_mail_service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.exeption.MException;
import ru.agronom.springboot_mail_service.service.MailSendService;
import ru.agronom.springboot_mail_service.service.MessageValidateService;

import javax.annotation.Resource;
import javax.validation.ValidationException;

@RestController
public class MailController {

    @Resource(name = "messageValidateService")
    MessageValidateService validateService;

    @Resource(name = "mailSendService")
    MailSendService sendService;

    @PostMapping("/mails")
    public ResponseEntity<Message> sendMail(@RequestBody Message message) {
        HttpHeaders httpHeaders = new HttpHeaders();

        try{
            validateService.isValid(message);
            sendService.sendMessage(message);
            return new ResponseEntity<Message>(message,httpHeaders,HttpStatus.valueOf(202));


        } catch (ValidationException e){
            httpHeaders.add("ERROR",e.getMessage());
            return new ResponseEntity<Message>(message,httpHeaders,HttpStatus.valueOf(400));

        } catch (MailException e){
            httpHeaders.add("ERROR",e.getMessage());
            return new ResponseEntity<Message>(message,httpHeaders,HttpStatus.valueOf(500));

        }

    }
}
