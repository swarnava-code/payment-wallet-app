package com.sclab.boot.exchangerateprovider.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    private boolean success;

    private String terms;

    private String privacy;

    private long timestamp;

    private String source;

    private Map<String, Double> quotes;

}