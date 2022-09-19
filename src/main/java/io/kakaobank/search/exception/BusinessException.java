package io.kakaobank.search.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private HttpStatus code;

    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    };

    public BusinessException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public HttpStatus getCode() {
        return this.code;
    }
}
