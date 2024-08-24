package com.trustyourfeet.overhang.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trustyourfeet.overhang.config.RabbitMqConfig;
import com.trustyourfeet.overhang.event.AccountRegistrationEvent;

@Service
public class RabbitMqService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqService.class);

    @Autowired
    private ProfileService profileService;

    @RabbitListener(queues = RabbitMqConfig.CREATE_IDENTITY_PROFILE_QUEUE)
    public void createIdentityProfile(AccountRegistrationEvent event) {
        logger.info("Received Account Registration Event for username: {}", event.getUsername());
        profileService.createProfile(event.getId(), event.getUsername(), event.getEmail());
    }
}
