package com.sclab.boot.paymentwalletapp.repository;

import com.sclab.boot.paymentwalletapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> { }