package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.enumeration.WalletStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC_NAME = "email_msg";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendOnboardingNotification(String name, WalletStatus status, UUID walletId) {
        String message = String.format("Welcome %s, Your wallet is created and %s.\n Your Wallet id is %s",
                name, status, walletId);
        kafkaTemplate.send(TOPIC_NAME, walletId.toString(), message);
        logger.info("onboarding message published to kafka");
    }

}