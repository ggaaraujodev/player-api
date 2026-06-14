package org.github.minecraft.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private record ErrorDetail(String message, int status) {}
    private record ErrorResponse(boolean success, int error_code, ErrorDetail response) {}

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Not Found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ex.printStackTrace();
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        var body = new ErrorResponse(false, status.value(), new ErrorDetail(message, status.value()));
        return ResponseEntity.status(status).body(body);
    }
}
