package com.sclab.boot.paymentwalletapp.advice;

import com.sclab.boot.paymentwalletapp.util.CustomResponseEntity;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalAdviser {
    private static final Logger logger = LoggerFactory.getLogger(GlobalAdviser.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException cve) {
        Set<String> victimFields = cve.getConstraintViolations().stream()
                .map(v->v.getPropertyPath().toString()).collect(Collectors.toSet());
        List<String> listOfConstraintViolation = cve.getConstraintViolations()
                .stream()
                .map(v -> String.format("%s | %s", v.getPropertyPath(), v.getMessage()))
                .collect(Collectors.toList());
        logger.error(cve.getConstraintViolations().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponseEntity.keyValuePairsToMap(
                        "message", "Validation Failed - constraintViolationException",
                        "violations", listOfConstraintViolation,
                        "victimFields", victimFields
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(MethodArgumentTypeMismatchException mismatchException) {
        mismatchException.printStackTrace();
        return ResponseEntity.badRequest().body(
                CustomResponseEntity.keyValuePairsToMap(
                        "victimVariableName", mismatchException.getName(),
                        "error", "Bad Request - Method Argument Type Mismatch",
                        "hint", "Pass correct type in path variable or query param",
                        "message", mismatchException.getMessage()
                )
        );
    }

}