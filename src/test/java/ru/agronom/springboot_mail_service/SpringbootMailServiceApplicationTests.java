package ru.agronom.springboot_mail_service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.test.context.ActiveProfiles;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.service.MailSendService;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
class SpringbootMailServiceApplicationTests {

    @Autowired
    MailSendService mailSendService;

    private GreenMail greenMail;
    private Message message;


    @BeforeEach
    private void setUp() {
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();
        greenMail.setUser("user", "pass");
        message = new Message();
        message.setFrom("n@m.com");
        message.setTo("i@m.com");
        message.setSubject("subj");
        message.setText("test");

    }

    @AfterEach
    private void tearDown() {
        greenMail.stop();
    }


    @Test
    void shouldSendSingleMail() throws MessagingException, IOException {

        mailSendService.sendMessage(message);
        MimeMessage[] rMessage = greenMail.getReceivedMessages();
        MimeMessage curMessage = rMessage[0];
        assertEquals(message.getSubject(), curMessage.getSubject());
        assertEquals(message.getTo(), curMessage.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(curMessage.getContent()).contains(message.getText()));

    }
    @Ignore
    @Test
    void errorWithAuthentication(){
        greenMail.stop();
        greenMail = new GreenMail(new ServerSetup(50,null,"smtp"));
        greenMail.start();
        assertThrows(MailAuthenticationException.class,()-> mailSendService.sendMessage(message));

    }

}
