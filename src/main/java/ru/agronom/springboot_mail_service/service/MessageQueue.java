package ru.agronom.springboot_mail_service.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class MessageQueue implements IMessageQueue {
    private final Queue<Message> messageQueue = new LinkedList<>();

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
