package com.sclab.boot.paymentwalletapp.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private Map<String, Object> error;
}