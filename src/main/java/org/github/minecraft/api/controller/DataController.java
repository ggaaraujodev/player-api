package org.github.minecraft.api.controller;

import org.github.minecraft.api.users.UserService;
import org.github.minecraft.api.users.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class DataController {

    private final UserService userService;

    public DataController(UserService userService) {
        this.userService = userService;
    }

    private record ErrorDetail(String message, int status) {}
    private record ErrorResponse(boolean success, int error_code, ErrorDetail response) {}
    private record SuccessResponse(boolean success, UserResponseDTO response) {}

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{nick}")
    public ResponseEntity<?> getUserByNick(@PathVariable String nick) {
        return userService.findByNick(nick)
            .<ResponseEntity<?>>map(user ->
                ResponseEntity.ok(new SuccessResponse(true, user))
            )
            .orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(false, 404, new ErrorDetail("Not Found", 404)))
            );
    }
}
