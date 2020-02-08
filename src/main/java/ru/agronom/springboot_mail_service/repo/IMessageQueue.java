package ru.agronom.springboot_mail_service.repo;

import org.springframework.stereotype.Repository;
import ru.agronom.springboot_mail_service.domain.Message;

@Repository
public interface IMessageQueue {
    boolean offer(Message message);
    Message poll();
    boolean isNext();



}
