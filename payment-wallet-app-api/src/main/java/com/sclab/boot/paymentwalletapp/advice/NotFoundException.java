package com.sclab.boot.paymentwalletapp.advice;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }

}