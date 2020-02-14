package ru.agronom.springboot_mail_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.service.MessageQueueService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MailValidationTest {

    @Mock
    MessageQueueService service;

    private List<Message> rezTest;
    private Message m1, m2;

    private static Stream<String> getNotValidEmails() {
        return Stream.of(".s@com", "s.@com", "s.s", "@", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq@com");
    }

    @BeforeEach
    void init() {
        rezTest = new ArrayList<>();
        m1 = new Message("a@s.com", "s@s.com", "s", "t");
        m2 = new Message("a@s.com", "s@s.com", "s", "t");
    }

    @Test
    void validMessage() {
        service.offer(m1);
        Mockito.verify(service, Mockito.times(5)).offer(m1);
    }

    @Test
    void nullSubj() {
        m1.setFrom("s@s.com");
        m1.setTo("e@e.com");
        m1.setSubject(null);
        service.offer(m1);
        service.offer(m2);
        while (service.isNext()) {
            rezTest.add(service.poll());
        }
        assertEquals(1, rezTest.size());
    }

    @Test
    void emptySubj(){
        m1.setFrom("s@s.com");
        m1.setTo("e@e.com");
        m1.setSubject("");
        service.offer(m1);
        service.offer(m2);
        while (service.isNext()) {
            rezTest.add(service.poll());
        }
        assertEquals(1, rezTest.size());
    }

    @ParameterizedTest
    @MethodSource("getNotValidEmails")
    void notValidTo(String s) {
        Assertions.assertThrows(ValidationException.class, () -> {
            m1.setTo(s);
            service.offer(m1);
        });
    }

    @ParameterizedTest
    @MethodSource("getNotValidEmails")
    void notValidEmail(String s) {
        Assertions.assertThrows(ValidationException.class, () -> {
            m1.setFrom(s);
            service.offer(m1);
        });
    }
}
