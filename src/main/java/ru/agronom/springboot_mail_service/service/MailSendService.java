package ru.agronom.springboot_mail_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.exeption.MException;
import javax.validation.ValidationException;



import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Optional;

@Service
public class MailSendService {

    @Resource
    private JavaMailSender mailSender;

    private final Logger LOG = LoggerFactory.getLogger(MailSendService.class);


    public void sendMessage(Message message) throws MException {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(message.getFrom());
            mailMessage.setTo(message.getTo());
            mailMessage.setSubject(message.getSubject());
            mailMessage.setText(message.getText());
            mailSender.send(mailMessage);
            LOG.info("1 mail send ::" + Calendar.getInstance().getTime());
        } catch (MailParseException e) {
            LOG.error("Incomming message: {}; MailParseException: {}", message.toString(), e.getMessage());
            throw new ValidationException("MailParseException "+ e.getMessage());
        } catch (MailAuthenticationException e) {
            LOG.error("in case of authentication failure" + e.getMessage());
            throw new MException("in case of authentication failure " + e.getMessage());
        } catch (MailSendException e) {
            LOG.error("in case of send failure" + e.getMessage());
            throw new MException("in case of send failure " + e.getMessage());

    }
}}
