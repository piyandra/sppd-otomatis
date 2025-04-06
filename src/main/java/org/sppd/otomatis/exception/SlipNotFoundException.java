package org.sppd.otomatis.exception;

import org.springframework.http.HttpStatus;

public class SlipNotFoundException extends WebException {
    public SlipNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
