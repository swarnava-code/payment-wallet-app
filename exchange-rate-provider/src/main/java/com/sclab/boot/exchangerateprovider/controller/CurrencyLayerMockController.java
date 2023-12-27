package com.sclab.boot.exchangerateprovider.controller;

import com.sclab.boot.exchangerateprovider.util.FileUtil;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * We can use it in non-prod environment
 */
@RestController
public class CurrencyLayerMockController {

    @GetMapping("/api/live")
    public ResponseEntity<String> getMockData(@RequestParam String access_key,
                                                  @RequestParam String source) {
        String JSON_PATH = "src/main/resources/exchange-rate.json";
        JSONObject rootObject = new JSONObject(FileUtil.readFile(JSON_PATH));
        return ResponseEntity.status(HttpStatus.OK).body(rootObject.toString(2));
    }

}