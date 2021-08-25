package ru.dromran.testtz.config;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.exception.AbstractRequestException;

@ControllerAdvice
@Component
public class RequestExceptionHandler {


    @ExceptionHandler(AbstractRequestException.class)
    public ResponseEntity<ResponseMessageDTO> handleAbstractRequestException(AbstractRequestException ex,
                                                                             WebRequest webRequest) {
        return ResponseMessageDTO.getResponseEntity(ex.getExceptionStatus(), ex.getMessage());
    }
}
