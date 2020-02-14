package ru.agronom.springboot_mail_service.service;


import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessageQueueService implements IMessageQueue {

    @Resource(name = "defaultValidator")
    Validator validator;
    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean offer(Message message) throws ValidationException {
        Set<ConstraintViolation<Message>> validateMessageRez = validator.validate(message);
        for (ConstraintViolation<Message> violation : validateMessageRez) {
            throw new ValidationException(violation.getMessage());
        }

        return messageQueue.offer(message);
    }

    @Override
    public Message poll() {
        return messageQueue.poll();
    }

    @Override
    public boolean isNext() {
        return !messageQueue.isEmpty();
    }
}
