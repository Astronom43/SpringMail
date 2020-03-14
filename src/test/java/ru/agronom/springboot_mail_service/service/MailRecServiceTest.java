package ru.agronom.springboot_mail_service.service;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import ru.agronom.springboot_mail_service.domain.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MailRecServiceTest {


    @Autowired
    RabbitTemplate template;
    @Autowired
    Queue queue;
    @Mock
    MessageValidateService valid;
    @Mock
    MailSendService sendService;
    @Autowired
    MailRecService mailRecService;

    Message message, mrez;


    @BeforeEach
    void initTest() {

        message = new Message();
        message.setFrom("s@s.s");
        message.setTo("c@c.c");
        message.setSubject("subj");
        message.setText("text");
    }

    @Test
    void receiveMessage() throws InterruptedException {

        Mockito.when(valid.isValid(any(Message.class))).thenReturn(true);
        Mockito.doAnswer(invocationOnMock -> {
            mrez = invocationOnMock.getArgument(0);
            return null;
        }).when(sendService).sendMessage(any(Message.class));

        ReflectionTestUtils.setField(mailRecService,"validateService",valid);
        ReflectionTestUtils.setField(mailRecService,"mailSendService",sendService);
        template.convertAndSend(queue.getName(), message);
        Thread.sleep(500);

        Mockito.verify(valid,Mockito.times(1)).isValid(any(Message.class));

       Mockito.verify(sendService, Mockito.times(1)).sendMessage(any(Message.class));

       assertEquals(message.getText(), mrez.getText());


    }
}