package ru.agronom.springboot_mail_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;

import java.util.Calendar;

@Service
@EnableScheduling
public class MailSendService {

    private final JavaMailSender mailSender;
    private final MessageQueue messageQueue;
    private final Logger logger = LoggerFactory.getLogger(MailSendService.class);

    public MailSendService(JavaMailSender mailSender, MessageQueue messageQueue) {
        this.mailSender = mailSender;
        this.messageQueue = messageQueue;
    }

    public void sendMessage(Message message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(message.getFrom());
            mailMessage.setTo(message.getTo());
            mailMessage.setSubject(message.getSubject());
            mailMessage.setText(message.getText());
            mailSender.send(mailMessage);
            logger.info("mail send ::" + Calendar.getInstance().getTime());
        } catch (MailParseException e) {
            logger.error("in case of message creation failure" + e.getMessage());
        } catch (MailAuthenticationException e) {
            logger.error("in case of authentication failure" + e.getMessage());
        } catch (MailSendException e) {
            logger.error("in case of send failure" + e.getMessage());
        } catch (MailException e){
            logger.error("mail exceprion "+e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void run(){
        if (messageQueue.isNext()){
            sendMessage(messageQueue.poll());
        }
    }



}
