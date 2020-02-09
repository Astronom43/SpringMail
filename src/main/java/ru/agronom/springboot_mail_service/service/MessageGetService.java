package ru.agronom.springboot_mail_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.agronom.springboot_mail_service.domain.Message;
import ru.agronom.springboot_mail_service.repo.IMessageQueue;

import java.util.Calendar;

@Service
public class MessageGetService {

    private final Logger LOG = LoggerFactory.getLogger(MessageGetService.class);
    private final IMessageQueue messageQueue;
    @Value("${mail-get}")
    private String mailGet;

    public MessageGetService(@Qualifier(value = "messageQueueService") IMessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }


    public void getMail() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        Message[] messages = restTemplate.getForObject(mailGet, Message[].class);
        if (messages != null) {
            LOG.info(messages.length + " mail get::" + Calendar.getInstance().getTime());
            for (Message m : messages) {
                messageQueue.offer(m);
            }
        }
    }

}
