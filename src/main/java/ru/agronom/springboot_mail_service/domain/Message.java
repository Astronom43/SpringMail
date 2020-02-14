package ru.agronom.springboot_mail_service.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Message {

    @NotNull(message = "email must be set")
    @Email(message = "email must be valid")
    private String from;

    @NotNull(message = "email must be set")
    @Email(message = "email must be valid")
    private String to;

    @NotBlank(message = "Subject must ")
    @Size(min = 1, message = "Subject of email must not be empty.")
    private String subject;

    private String text;

    public Message() {
    }

    public Message(String from, String to, String subject, String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
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
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
