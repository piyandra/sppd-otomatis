package org.sppd.otomatis.controller;

import org.sppd.otomatis.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BankErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<Map<String, String>> handleMethodNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return WebResponse.<Map<String, String>>builder()
                .message("Data Tidak Valid")
                .data(errors)
                .build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .build();
    }
}
