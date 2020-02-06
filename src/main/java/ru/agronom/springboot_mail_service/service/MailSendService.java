package ru.agronom.springboot_mail_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;

@Service
public class MailSendService {

    private final JavaMailSender mailSender;

    public MailSendService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(Message message)  {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(message.getFrom());
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getText());
        mailSender.send(mailMessage);

    }


}
