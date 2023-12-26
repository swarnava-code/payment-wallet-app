package com.sclab.boot.exchangerateprovider.service;

import com.sclab.boot.exchangerateprovider.entity.ExchangeRate;
import com.sclab.boot.exchangerateprovider.repository.ExchangeRateRepository;
import com.sclab.boot.exchangerateprovider.util.FileUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Base is USD = $1
 */
@Service
public class ExchangeRateService {
    private final static Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Scheduled(cron = "59 * * * * *")
    public void getScheduledExchangeRate(){
        String JSON_PATH = "src/main/resources/exchange-rate.json";
        JSONObject rootObject = new JSONObject(FileUtil.readFile(JSON_PATH));
        logger.info(rootObject.toString());
        JSONObject ratesObject = rootObject.getJSONObject("quotes");
        ExchangeRate exchangeRate = new ExchangeRate(ratesObject.toString(), "apilayer.net");
        exchangeRateRepository.save(exchangeRate);
    }

}