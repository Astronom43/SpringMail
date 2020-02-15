package ru.agronom.springboot_mail_service.domain;

import org.springframework.beans.factory.annotation.Value;
import ru.agronom.springboot_mail_service.domain.validator.EmailArray;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;


public class Message {


    private final static String NOT_NULL_MESSAGE = "email must be set";
    private final static String EMAIL = "email must be valid";
    private final static String SUBJ_NOT_BLANK = "Subject of email must be set";


    @NotNull(message = NOT_NULL_MESSAGE)
    @Email(message = EMAIL)
    private String from;

    @NotNull(message = NOT_NULL_MESSAGE)
    @EmailArray(message = EMAIL)
    private String[] to;

    @NotBlank(message = SUBJ_NOT_BLANK)
    private String subject;

    private String text;

    public Message() {
    }

    public Message(String from, String subject, String text) {
        this.from = from;
        this.subject = subject;
        this.text = text;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String... to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to=" + Arrays.toString(to) +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return from.equals(message.from) &&
                Arrays.equals(to, message.to) &&
                subject.equals(message.subject) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(from, subject, text);
        result = 31 * result + Arrays.hashCode(to);
        return result;
    }
}
