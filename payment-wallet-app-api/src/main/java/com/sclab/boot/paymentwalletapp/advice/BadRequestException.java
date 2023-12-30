package com.sclab.boot.paymentwalletapp.advice;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message){
        super(message);
    }

}