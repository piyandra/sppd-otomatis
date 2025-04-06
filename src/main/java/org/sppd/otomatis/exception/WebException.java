package org.sppd.otomatis.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
public class WebException extends RuntimeException{

    public String message;
    @Getter
    public HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return message;
    }

    public WebException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
