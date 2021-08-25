package ru.dromran.testtz.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenRequestException extends AbstractRequestException {

    public ForbiddenRequestException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
