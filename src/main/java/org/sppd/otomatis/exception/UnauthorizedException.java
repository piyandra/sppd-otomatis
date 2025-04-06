package org.sppd.otomatis.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends WebException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
