package ru.agronom.springboot_mail_service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.exeption.MException;
import ru.agronom.springboot_mail_service.service.MailSendService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
class SpringbootMailServiceApplicationTests {

    @Autowired
    MailSendService mailSendService;

    private GreenMail greenMail;
    private Message message;

    private static Stream<Arguments> getTo() {
        return Stream.of(
                Arguments.of((Object) new String[]{".s@com", "s@com"}),
                Arguments.of((Object) new String[]{"s.@com", "s@com"}),
                Arguments.of((Object) new String[]{"@"})
        );
    }


    @BeforeEach
    private void setUp() {
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();
        greenMail.setUser("user", "pass");
        message = new Message();
        message.setFrom("n@m.com");
        message.setTo("i@m.com", "a@a.com");
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
        assertEquals(message.getTo()[0], curMessage.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(curMessage.getContent()).contains(message.getText()));
        assertTrue(curMessage.getAllRecipients().length == 2);

    }

    @Test
    void errorConnectServer() {
        greenMail.stop();
        greenMail = new GreenMail(new ServerSetup(50, null, "smtp"));
        greenMail.start();
        Exception e = assertThrows(MException.class, () -> mailSendService.sendMessage(message));
        assertTrue(e.getMessage().contains("in case of send failure"));
    }

    @Test
    void errorAuthentication() {
        greenMail.stop();
        greenMail = new GreenMail((new ServerSetup(2525, null, "smtp")));
        greenMail.setUser("a", "b");
        greenMail.start();
        Exception e = assertThrows(MException.class, () -> mailSendService.sendMessage(message));
        assertTrue(e.getMessage().contains("in case of authentication failure"));
    }

    @ParameterizedTest()
    @MethodSource("getTo")
    void errorParseMessageTo(String[] s) {
        message.setTo(s);
        Exception e = assertThrows(ValidationException.class, () -> mailSendService.sendMessage(message));
        assertTrue(e.getMessage().contains("MailParseException "));
    }

}
