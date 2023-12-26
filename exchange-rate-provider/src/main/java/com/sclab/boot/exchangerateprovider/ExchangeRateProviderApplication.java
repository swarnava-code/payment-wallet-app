package com.sclab.boot.exchangerateprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeRateProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateProviderApplication.class, args);
	}

}