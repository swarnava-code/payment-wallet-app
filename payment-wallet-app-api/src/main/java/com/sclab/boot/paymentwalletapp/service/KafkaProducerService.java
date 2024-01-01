package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.entity.Transaction;
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

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendOnboardingNotification(String name, WalletStatus status, UUID walletId) {
        final String TOPIC_NAME = "email_msg";
        String message = String.format("Welcome %s, Your wallet is created and %s.\n Your Wallet id is %s",
                name, status, walletId);
        kafkaTemplate.send(TOPIC_NAME, walletId.toString(), message);
        logger.info("onboarding message published to kafka");
    }

    public void sendTransactionNotification(Transaction transaction) {
        final String TOPIC_NAME = "OTP";
        String message = String.format("You paid %s to %s",
                transaction.getAmount(), transaction.getReceiver().getUser().getName());
        kafkaTemplate.send(TOPIC_NAME, transaction.getSender().getId().toString(), message);
        logger.info("Kafka message published: one entity paid another entity");
    }

}