package org.sppd.otomatis.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.sppd.otomatis.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class UserControllerErrorHandler {
    public static Logger getLog() {
        return log;
    }

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
        if (e.getMessage().contains("JSON")){
            return WebResponse.<String>builder()
                    .message("Error")
                    .build();
        }
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        String[] parts = e.getMessage().split(":", 2);
        if (parts.length == 2) {
            errors.put(parts[0].trim(), parts[1].trim());
        } else {
            errors.put("error", e.getMessage());
        }
        log.info("Error Handle {}", e.getMessage());

        return WebResponse.<Map<String, String>>builder()
                .message("Data Tidak Valid")
                .data(errors)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        if (cause instanceof IllegalArgumentException) {
            return WebResponse.<String>builder()
                    .message(cause.getMessage())
                    .build();
        }
        return WebResponse.<String>builder()
                .message("Data Tidak Valid")
                .build();
    }

}
