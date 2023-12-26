package com.sclab.boot.exchangerateprovider.service;

import com.sclab.boot.exchangerateprovider.util.FileUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateService {

    @Scheduled(cron = "59 * * * * *")
    public void getScheduledExchangeRate(){
        System.out.println(System.currentTimeMillis());
        System.out.println(FileUtil.readFile("src/main/resources/exchange-rate.json"));
    }

}