package com.sclab.boot.exchangerateprovider.repository;

import com.sclab.boot.exchangerateprovider.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> { }