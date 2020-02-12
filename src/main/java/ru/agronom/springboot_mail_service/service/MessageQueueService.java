package ru.agronom.springboot_mail_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessageQueueService implements IMessageQueue {

    @Resource(name = "defaultValidator")
    Validator validator;
    private final Logger LOG = LoggerFactory.getLogger(MessageQueueService.class);
    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean offer(Message message) {
        Set<ConstraintViolation<Message>> validateMessageRez = validator.validate(message);
        if (validateMessageRez.size()==0){
            return messageQueue.offer(message);
        } else {
            validateMessageRez.forEach(e->LOG.error(e.getMessage()));
            return false;
        }

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
