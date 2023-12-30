package com.sclab.boot.paymentwalletapp.controller;

import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping
    public List<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable UUID id) {
        return walletService.getWalletById(id)
                .map(wallet -> new ResponseEntity<>(wallet, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId,
                                               @RequestBody Wallet wallet) {
        return new ResponseEntity<>(walletService.createWallet(userId, wallet), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable UUID id, @RequestBody Wallet updatedWallet) {
        Wallet updated = walletService.updateWallet(id, updatedWallet);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable UUID id) {
         walletService.deleteWallet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}