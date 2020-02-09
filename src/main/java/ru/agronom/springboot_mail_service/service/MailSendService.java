package ru.agronom.springboot_mail_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;

import javax.annotation.Resource;
import java.util.Calendar;

@Service
@EnableScheduling
public class MailSendService {

    @Resource
    private JavaMailSender mailSender;

    private final Logger LOG = LoggerFactory.getLogger(MailSendService.class);



    public void sendMessage(Message message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(message.getFrom());
            mailMessage.setTo(message.getTo());
            mailMessage.setSubject(message.getSubject());
            mailMessage.setText(message.getText());
            mailSender.send(mailMessage);
            LOG.info("1 mail send ::" + Calendar.getInstance().getTime());
        } catch (MailParseException e) {
            LOG.error("in case of message creation failure" + e.getMessage());
        } catch (MailAuthenticationException e) {
            LOG.error("in case of authentication failure" + e.getMessage());
        } catch (MailSendException e) {
            LOG.error("in case of send failure" + e.getMessage());
        } catch (MailException e){
            LOG.error("mail exceprion "+e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }




}
