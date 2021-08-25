package ru.dromran.testtz.exception;

import org.springframework.http.HttpStatus;

public class AbstractRequestException extends RuntimeException {

    protected final String message;
    protected final HttpStatus exceptionStatus;

    public AbstractRequestException(String message, HttpStatus exceptionStatus) {
        super(message);
        this.message = message;
        this.exceptionStatus = exceptionStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getExceptionStatus() {
        return exceptionStatus;
    }
}
