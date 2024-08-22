package com.trustyourfeet.overhang.service;

import com.trustyourfeet.overhang.entity.Profile;
import com.trustyourfeet.overhang.event.AccountRegistrationEvent;
import com.trustyourfeet.overhang.repository.ProfileRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private ProfileRepository profileRepository;

    @KafkaListener(topics = "auth.account.registration", groupId = "identity-service-group")
    public void consumeRegistrationEvent(AccountRegistrationEvent event) {
        Profile profile = new Profile();
        profile.setId(event.getId());
        profile.setUsername(event.getUsername());
        profile.setEmail(event.getEmail());

        profileRepository.save(profile);
        logger.info("Profile registered from account: {} ({})", event.getUsername(), event.getId());
    }
}
