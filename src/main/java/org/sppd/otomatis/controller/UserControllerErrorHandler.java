package org.sppd.otomatis.controller;

import org.sppd.otomatis.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserControllerErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();

        for (FieldError errors : e.getBindingResult().getFieldErrors()) {
            error.put(errors.getField(), errors.getDefaultMessage());
        }
        return WebResponse.<Map<String, String>>builder()
                .message("Data Tidak valid")
                .data(error)
                .build();

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public WebResponse<String> handleRuntimeException(RuntimeException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .build();
    }

}
