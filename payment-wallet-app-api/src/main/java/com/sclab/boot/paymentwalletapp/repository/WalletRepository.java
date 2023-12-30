package com.sclab.boot.paymentwalletapp.repository;

import com.sclab.boot.paymentwalletapp.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Query(value = "SELECT * from Wallet w WHERE w.username = :username", nativeQuery = true)
    Wallet findByUsername(String username);

}