package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(UUID id) {
        return walletRepository.findById(id);
    }

    public Wallet createWallet(Wallet wallet) {
        wallet.setId(null);
        return walletRepository.save(wallet);
    }

    public Wallet updateWallet(UUID id, Wallet updatedWallet) {
        updatedWallet.setId(id);
        return walletRepository.findById(id).map(wallet -> {
            updatedWallet.setUsername(wallet.getUsername());
            wallet.setWalletOrDefault(updatedWallet);
            return walletRepository.save(wallet);
        }).orElse(null);
    }

    public void deleteWallet(UUID id) {
        walletRepository.deleteById(id);
    }

}