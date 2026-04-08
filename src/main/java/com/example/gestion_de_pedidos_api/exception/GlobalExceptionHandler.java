package com.example.gestion_de_pedidos_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error: ", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,String>> handleOutOfStock(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error: ", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String,String>> handleIllegalState(IllegalStateException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Error: ", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGlobalExceptions(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error inesperado: ", ex.getMessage()));
    }
}
