package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.advice.BadRequestException;
import com.sclab.boot.paymentwalletapp.entity.Transaction;
import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.enumeration.TransactionStatus;
import com.sclab.boot.paymentwalletapp.repository.TransactionRepository;
import com.sclab.boot.paymentwalletapp.repository.WalletRepository;
import com.sclab.boot.paymentwalletapp.util.TimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public Transaction createTransaction(UUID senderId, UUID receiverId, BigDecimal amount, String notes) {
        Wallet sender = walletRepository.findById(senderId).orElse(null);
        Wallet receiver = walletRepository.findById(receiverId).orElse(null);
        // Create and save the transaction
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .notes(notes)
                .status(TransactionStatus.FAILURE)
                .build();
        if (sender == null) {
            throw new BadRequestException("transaction failed: sender not exist"
                    + transactionRepository.save(transaction));
        }
        if (receiver == null) {
            throw new BadRequestException("transaction failed: receiver not exist"
                    + transactionRepository.save(transaction));
        }
        if (sender.getBalance().compareTo(amount) <= 0) {
            var t = transactionRepository.save(transaction);
            throw new BadRequestException("transaction failed: sender have insufficient balance " + t);
        }
        if (sender.getLimitPerTransaction() < amount.intValue()) {
            throw new BadRequestException("transaction failed: It shouldn't cross 'LimitPerTransaction' value"
                    + transactionRepository.save(transaction));
        }

        BigDecimal sumOfAllTransactionInLast24Hours =
                transactionRepository.findSumOfAllTransactionInLast24Hours(senderId.toString(), TimeUtil.currentDate().toString());
        int sumOfAllTransaction = (sumOfAllTransactionInLast24Hours == null) ? 0 : sumOfAllTransactionInLast24Hours.intValue();
//        if (sumOfAllTransaction > sender.getLimitTotalAmountPerDay()) {
        int limitTotalAmountPerDay = sender.getLimitTotalAmountPerDay();
        // 100 > 10
        if (limitTotalAmountPerDay < (sumOfAllTransaction + amount.intValue())) {
            transactionRepository.save(transaction);
            throw new BadRequestException(
                    String.format("transaction failed: You shouldn't cross the value of 'limitTotalAmountPerDay'" +
                                    " \nYou can pay maximum %s %s for today till 11:59 PM",
                            (limitTotalAmountPerDay - sumOfAllTransaction), sender.getCurrencyCode())
            );
        }

        transaction.setStatus(TransactionStatus.SUCCESS);
        // Update sender's balance
        sender.setBalance(sender.getBalance().subtract(amount));
        walletRepository.save(sender);
        // Update receiver's balance
        receiver.setBalance(receiver.getBalance().add(amount));
        walletRepository.save(receiver);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

}