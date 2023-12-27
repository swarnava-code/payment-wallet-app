package com.sclab.boot.exchangerateprovider.service;

import com.sclab.boot.exchangerateprovider.entity.ExchangeRate;
import com.sclab.boot.exchangerateprovider.model.ExchangeRateResponse;
import com.sclab.boot.exchangerateprovider.repository.ExchangeRateJSONRepository;
import com.sclab.boot.exchangerateprovider.repository.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * Base is USD = $1
 */
@Service
public class ExchangeRateService {
    private final static Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    @Autowired
    ExchangeRateJSONRepository exchangeRateJSONRepository;

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Value("${external.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "59 * * * * *")
    public void getScheduledExchangeRate(){
        ExchangeRateResponse responses = restTemplate.getForObject(apiUrl, ExchangeRateResponse.class);
        Map<String, Double> exchangeRates = responses.getQuotes();
        exchangeRates.forEach((code, rate)->{
            ExchangeRate exchangeRate = new ExchangeRate(code.substring(3), rate);
            exchangeRateRepository.save(exchangeRate);
        });
        logger.info("exchange rate updated.");
    }

}