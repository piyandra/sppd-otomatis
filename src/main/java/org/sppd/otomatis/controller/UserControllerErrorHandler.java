package org.sppd.otomatis.controller;

import org.sppd.otomatis.dto.WebResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public WebResponse<String> handleRuntimeException(RuntimeException e) {
        return WebResponse.<String>builder()
                .message(e.getMessage())
                .build();
    }
}
