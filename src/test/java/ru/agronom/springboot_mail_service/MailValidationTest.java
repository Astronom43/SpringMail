package ru.agronom.springboot_mail_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.service.MessageValidateService;

import javax.validation.ValidationException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MailValidationTest {

    @Autowired
    MessageValidateService service;
    private Message m1, m2;

    private static Stream<String> getNotValidEmailsFrom() {
        return Stream.of(".s@com", "s.@com", "s.s", "@", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq@com");
    }

    private static Stream<Arguments> getNotValidEmailsTo() {
//        ArrayList<String[]> list = new ArrayList<>();
//        list.add(new String[]{".s@com", "s@com"});
//        list.add(new String[]{"s.@com", "s@com"});
//        list.add(new String[]{"s@com", "s1@com", "s.s"});
//        list.add(new String[]{"@"});
//        list.add(new String[]{"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq@com", "s@com"});
        return Stream.of(
                Arguments.of((Object) new String[]{".s@com", "s@com"}),
                Arguments.of((Object) new String[]{"s.@com", "s@com"}),
                Arguments.of((Object) new String[]{"s@com", "s1@com", "s.s"}),
                Arguments.of((Object) new String[]{"@"}),
                Arguments.of((Object) new String[]{"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq@com", "s@com"})
        );
    }

    @BeforeEach
    void init() {
        m1 = new Message("a@s.com", "s", "t");
        m1.setTo("a@com");
        m2 = new Message("a@s.com", "s", "t");
        m2.setTo("a@com", "s@ru", "a.a@com");
    }

    @Test
    void validMessage() {
        assertTrue(service.isValid(m1));
        assertTrue(service.isValid(m2));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void blankSubj(String subj) {
        m1.setSubject(subj);
        ValidationException exception = assertThrows(ValidationException.class, () -> service.isValid(m1));
        assertTrue(exception.getMessage().contains("be set"));
    }


    @ParameterizedTest
    @MethodSource("getNotValidEmailsTo")
    void notValidToIsArray(String[] s) {
        m1.setTo(s);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.isValid(m1);
        });
        assertTrue(exception.getMessage().contains("be valid"));
    }

    @ParameterizedTest
    @MethodSource("getNotValidEmailsFrom")
    void notValidToIsSingle(String s){
        m1.setTo(s);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.isValid(m1);
        });
        assertTrue(exception.getMessage().contains("be valid"));
    }

    @ParameterizedTest
    @MethodSource("getNotValidEmailsFrom")
    void notValidFrom(String s) {
        m1.setFrom(s);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.isValid(m1);
        });
        assertTrue(exception.getMessage().contains("be valid"));
    }
}
