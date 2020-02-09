package ru.agronom.springboot_mail_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.agronom.springboot_mail_service.domain.Message;

@Service
public class MessageGetService {

   private final MessageQueue messageQueue;
    @Value ("${mail-get}")
   private String mailGet ;
    public MessageGetService(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }


    public void getMail(){
       RestTemplate restTemplate = new RestTemplate();

       Message[] messages = restTemplate.getForObject(mailGet,Message[].class);
       if (messages!=null){
           for (Message m : messages ) {
               messageQueue.offer(m);
           }
       }
   }

}
