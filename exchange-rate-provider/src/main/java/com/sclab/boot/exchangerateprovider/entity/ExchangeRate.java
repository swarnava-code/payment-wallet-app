package com.sclab.boot.exchangerateprovider.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base currency is USD = $1
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRate {

    @Id
    private String currencyCode;

    private Double currencyRate;

}