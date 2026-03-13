package com.myportfolio.my_portfolio_backend.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEmptyOrMalformedBody(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();

        if (ex.getMessage() != null && ex.getMessage().contains("Required request body is missing")) {
            error.put("error", "Has enviado un body vacío");
        } else {
            error.put("error", "El body tiene un formato JSON incorrecto");
        }

        return ResponseEntity.badRequest().body(error);
    }
}
