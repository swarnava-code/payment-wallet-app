package com.sclab.boot.paymentwalletapp.repository;

import com.sclab.boot.paymentwalletapp.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> { }