package ru.agronom.springboot_mail_service.service;


import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessageQueueService implements IMessageQueue {

    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean offer(Message message) {
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
