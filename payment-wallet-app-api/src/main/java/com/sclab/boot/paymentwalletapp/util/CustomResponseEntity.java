package com.sclab.boot.paymentwalletapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class CustomResponseEntity {

    public static ResponseEntity NOT_FOUND(Object... keyValuePairs) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(mapToErrorResponse(keyValuePairs));
    }

    public static ErrorResponse mapToErrorResponse(Object... keyValuePairs) {
        return new ErrorResponse(keyValuePairsToMap(keyValuePairs));
    }

    public static Map<String, Object> keyValuePairsToMap(Object... keyValuePairs) {
        Map<String, Object> map = new HashMap<>();
        int keyValuePairsSize = keyValuePairs.length;
        if (keyValuePairsSize % 2 == 0) {
            for (int i = 1; i < keyValuePairsSize; i += 2) {
                map.put(keyValuePairs[i - 1].toString(), keyValuePairs[i]);
            }
        }
        return map;
    }

}