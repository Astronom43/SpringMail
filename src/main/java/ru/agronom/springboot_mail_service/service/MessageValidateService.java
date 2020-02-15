package ru.agronom.springboot_mail_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;


@Service
public class MessageValidateService {

    private final Logger LOG = LoggerFactory.getLogger(MessageValidateService.class);

    @Resource(name = "defaultValidator")
    private Validator validator;

    public boolean isValid(Message message) throws ValidationException {
        Set<ConstraintViolation<Message>> violationSet = validator.validate(message);
        for (ConstraintViolation<Message> v : violationSet) {
            LOG.info(v.getMessage());
            throw new ValidationException(v.getMessage());
        }
        return true;
    }
}
