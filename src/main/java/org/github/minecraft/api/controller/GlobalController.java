package org.github.minecraft.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/**")
public class GlobalController {

    @RequestMapping
    public ResponseEntity<Map<String, Object>> handleNotFound() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> notFoundResponse = new HashMap<>();

        notFoundResponse.put("message", "Not Found");
        notFoundResponse.put("status", 404);

        response.put("success", false);
        response.put("error_code", 404);
        response.put("response", notFoundResponse);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
