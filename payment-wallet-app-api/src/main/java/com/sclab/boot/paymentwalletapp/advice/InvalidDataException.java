package com.sclab.boot.paymentwalletapp.advice;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

}