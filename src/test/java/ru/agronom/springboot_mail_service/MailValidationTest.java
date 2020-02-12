package ru.agronom.springboot_mail_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.service.MessageQueueService;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MailValidationTest {
    @Autowired
    MessageQueueService service;
    private List<Message> rezTest;
    private Message m1, m2;

    @BeforeEach
    void init(){
        rezTest = new ArrayList<>();
       m1= new Message("a@s.com","s@s.com","s","t");
       m2= new Message("a@s.com","s@s.com","s","t");
    }

    @Test
    void validMessage(){

        service.offer(m1);
        service.offer(m2);
        while (service.isNext()){
            rezTest.add(service.poll());
        }
        assertEquals(2,rezTest.size());
    }
    @Test
    void nullSubj(){
        m1.setFrom("s@s.com");
        m1.setTo("e@e.com");
        m1.setSubject(null);
        service.offer(m1);
        service.offer(m2);
        while (service.isNext()){
            rezTest.add(service.poll());
        }
        assertEquals(1,rezTest.size());
    }
    @Test
    void emptySubj(){
        m1.setFrom("s@s.com");
        m1.setTo("e@e.com");
        m1.setSubject("");
        service.offer(m1);
        service.offer(m2);
        while (service.isNext()){
            rezTest.add(service.poll());
        }
        assertEquals(1,rezTest.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {".s@com","s.@com","s.s","@","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq@com"})
    void notValidEmail(String s){
        m1.setFrom(s);
        m1.setTo("e@e.com");
        m1.setSubject("asd");
        service.offer(m1);
        service.offer(m2);
        while (service.isNext()){
            rezTest.add(service.poll());
        }
        assertEquals(1,rezTest.size());
    }
}
