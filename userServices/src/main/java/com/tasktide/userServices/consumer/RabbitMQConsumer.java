package com.tasktide.userServices.consumer;

import com.tasktide.userServices.model.User;
import com.tasktide.userServices.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {
    UserRepository userRepository;

    @Autowired
    public RabbitMQConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumer(User user) {
        log.info("Received json message to RabbitMQ");
        try {
            userRepository.createUser(user);
            log.info("User created successfully");
        } catch (Exception e) {
            log.error("Error while creating user", e);
        }
    }

}
