package com.sclab.boot.paymentwalletapp.controller;

import com.sclab.boot.paymentwalletapp.util.CustomResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok(CustomResponseEntity.keyValuePairsToMap(
                "message", "Hello User, Welcome to Spring Boot Payment Wallet App \uD83D\uDE4F",
                "status", "ACTIVE \uD83D\uDFE2"
        ));
    }

}