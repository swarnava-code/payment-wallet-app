package com.sclab.boot.exchangerateprovider.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateJSON {

    @JsonIgnore
    @Id
    private final Integer id = 1;

    @Lob
    private String jsonResponse;

    private String source;

}