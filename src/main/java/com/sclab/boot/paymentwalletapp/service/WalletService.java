package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.repository.UserRepository;
import com.sclab.boot.paymentwalletapp.repository.WalletRepository;
import com.sclab.boot.paymentwalletapp.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(UUID id) {
        return walletRepository.findById(id);
    }

    public ResponseEntity<Wallet> createWallet(Long userId, Wallet wallet) {
        var optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            return CustomResponseEntity.NOT_FOUND("message", "user not found: "+userId);
        }
        wallet.setId(null);
        wallet.setUser(optUser.get());
        return new ResponseEntity<>(walletRepository.save(wallet), HttpStatus.CREATED);
    }

    public Wallet updateWallet(UUID id, Wallet updatedWallet) {
        updatedWallet.setId(id);
        return walletRepository.findById(id).map(wallet -> {
            updatedWallet.setUser(wallet.getUser());
            wallet.setWalletOrDefault(updatedWallet);
            return walletRepository.save(wallet);
        }).orElse(null);
    }

    public void deleteWallet(UUID id) {
        walletRepository.deleteById(id);
    }

}