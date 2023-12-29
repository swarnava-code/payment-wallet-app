package com.sclab.boot.paymentwalletapp.advice;

public class MinBalanceNotMetException extends RuntimeException {

    public MinBalanceNotMetException(String message) {
        super(message);
    }

    public MinBalanceNotMetException(String message, Throwable cause) {
        super(message, cause);
    }
}
