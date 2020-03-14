package ru.agronom.springboot_mail_service.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.agronom.springboot_mail_service.domain.Message;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MailRecServiceTest {

    @Autowired
    MailRecService mailRecService;

    @Autowired
    RabbitTemplate template;

    Queue queue;

    private GreenMail greenMail;

    Message message;




    @BeforeEach
    void init(){
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();
        greenMail.setUser("user", "pass");
        queue = new Queue("mail",false);
        message = new Message();
        message.setFrom("s@s.com");
        message.setTo("c@c.com");
        message.setSubject("subject");
        message.setText("text");

    }

    @Test
    void receiveMessage() {
        template.convertAndSend(queue.getName(),message);
        MimeMessage[] rMessage = greenMail.getReceivedMessages();
        assertEquals(1,rMessage.length);
    }
}