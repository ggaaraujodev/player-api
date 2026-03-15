package org.github.minecraft.api.controller;

import org.github.minecraft.api.users.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class DataController {

    @Autowired
    private UserRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{nick}")
    public ResponseEntity<Map<String, Object>> getUserByNick(@PathVariable String nick) {
        Map<String, Object> response = new HashMap<>();

        return repository.findByNick(nick).map(user -> {
            response.put("success", true);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> dataMap = null;
            try {
                dataMap = objectMapper.readValue(user.getData(), Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response.put("success", false);
                response.put("error_code", 500);
                response.put("response", "Erro ao processar JSON");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            Map<String, Object> userData = new HashMap<>();
            userData.put("nick", user.getNick());
            userData.put("data", dataMap);

            response.put("response", userData);

            return ResponseEntity.ok(response);
        }).orElseGet(() -> {

            Map<String, Object> notFoundResponse = new HashMap<>();
            notFoundResponse.put("details", new HashMap<>());
            notFoundResponse.put("message", "Not Found");
            notFoundResponse.put("status", 404);

            response.put("success", false);
            response.put("error_code", 404);
            response.put("response", notFoundResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }
}
