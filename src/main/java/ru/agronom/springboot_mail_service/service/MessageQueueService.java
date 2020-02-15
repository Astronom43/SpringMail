package ru.agronom.springboot_mail_service.service;


import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessageQueueService implements IMessageQueue {

    @Resource(name = "messageValidateService")
    MessageValidateService validate;

    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean offer(Message message) throws ValidationException {
        validate.isValid(message);
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
