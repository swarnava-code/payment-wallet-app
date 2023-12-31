package com.sclab.boot.paymentwalletapp.advice;

import com.sclab.boot.paymentwalletapp.util.CustomResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * It will handle exception for project specific exception
 */
@RestControllerAdvice
public class ProjectSpecificAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(SpecialiseAdviser.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(BadRequestException e) {
        return CustomResponseEntity.BAD_REQUEST(
                "exceptionClass", e.getClass(),
                "error", "KNOWN BAD REQUEST",
                "message", e.getMessage()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleException(NotFoundException e) {
        return CustomResponseEntity.NOT_FOUND(
                "exceptionClass", e.getClass(),
                "error", "NOT FOUND",
                "message", e.getMessage()
        );
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(InvalidDataException e) {
        return CustomResponseEntity.NOT_FOUND(
                "exceptionClass", e.getClass(),
                "error", "Invalid Value",
                "message", e.getMessage()
        );
    }

}