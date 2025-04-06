package org.sppd.otomatis.exception;

import lombok.extern.slf4j.Slf4j;
import org.sppd.otomatis.dto.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class WebExceptionHandler {

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
    @ExceptionHandler(WebException.class)
    public ResponseEntity<WebResponse<String>> handleWebResponseError(WebException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(WebResponse.<String>builder()
                        .message(e.getMessage())
                        .build());
    }


}
