package com.sclab.boot.paymentwalletapp.advice;

import com.sclab.boot.paymentwalletapp.entity.Transaction;
import lombok.Getter;

@Getter
public class TransactionFailedException extends RuntimeException {
    private final Transaction transaction;

    public TransactionFailedException(String message, Transaction transaction) {
        super(message);
        this.transaction = transaction;
    }

}