package ru.agronom.springboot_mail_service.exeption;

import org.springframework.mail.MailException;

public class MException extends MailException {
    public MException(String msg) {
        super(msg);
    }


}
