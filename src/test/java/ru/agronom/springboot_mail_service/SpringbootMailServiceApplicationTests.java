package ru.agronom.springboot_mail_service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.service.MailSendService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class SpringbootMailServiceApplicationTests {

    @Autowired
    MailSendService mailSendService;

    private GreenMail greenMail;

    @Before
    public void setUp(){
        greenMail = new GreenMail(new ServerSetup(2525,null,"smtp"));
        greenMail.start();
        greenMail.setUser("user","pass");
    }
    @After
    public void tearDown(){
        greenMail.stop();
    }


    @Test
    void shouldSendSingleMail() throws MessagingException, IOException {
//        greenMail = new GreenMail(new ServerSetup(2525,null,"smtp"));
//        greenMail.start();
//        greenMail.setUser("user","pass");

        Message message = new Message();
        message.setFrom("n@m.com");
        message.setTo("i@m.com");
        message.setSubject("subj");
        message.setText("test");
        mailSendService.sendMessage(message);
        MimeMessage[] rMessage = greenMail.getReceivedMessages();
//        greenMail.stop();
        MimeMessage curMessage = rMessage[0];
        Assert.assertEquals(message.getSubject(),curMessage.getSubject());
        Assert.assertEquals(message.getTo(),curMessage.getAllRecipients()[0].toString());
        Assert.assertTrue(String.valueOf(curMessage.getContent()).contains(message.getText()));

    }

}
