package com.sclab.boot.exchangerateprovider.repository;

import com.sclab.boot.exchangerateprovider.entity.ExchangeRateJSON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateJSONRepository extends JpaRepository<ExchangeRateJSON, Integer> { }