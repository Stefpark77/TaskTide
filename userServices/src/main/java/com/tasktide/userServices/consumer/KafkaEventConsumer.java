package com.tasktide.userServices.consumer;

import com.tasktide.userServices.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventConsumer {
    UserRepository userRepository;

    @Autowired
    public KafkaEventConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}"
            , groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String username) {
        log.info("Received login event");
        log.info("New Login attempt made by username: {}", username);

        try{
            userRepository.addNewLogin(username);
        }catch (Exception e){
            log.error("Error while changing last login date for username: {}", username, e);
        }
    }
}
