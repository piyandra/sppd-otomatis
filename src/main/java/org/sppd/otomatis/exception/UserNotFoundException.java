package org.sppd.otomatis.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends WebException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
