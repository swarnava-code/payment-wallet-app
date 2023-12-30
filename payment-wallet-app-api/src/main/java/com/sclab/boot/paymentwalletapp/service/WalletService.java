package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.advice.BadRequestException;
import com.sclab.boot.paymentwalletapp.advice.NotFoundException;
import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(UUID id) {
        return walletRepository.findById(id);
    }

    public Wallet createWallet(Long userId, Wallet wallet) {
        var optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        Wallet existingWallet = walletRepository.findByUsername(optUser.get().getUsername());
        if (existingWallet != null) {
            throw new BadRequestException("existing wallet found for same user");
        }
        wallet.setId(null);
        wallet.setUser(optUser.get());
        return walletRepository.save(wallet);
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
        var optWallet = walletRepository.findById(id);
        if (optWallet.get().getBalance().intValue() != 0) {
            throw new BadRequestException("wallet balance should be 0 to delete");
        }
         walletRepository.deleteById(id);;
    }

}