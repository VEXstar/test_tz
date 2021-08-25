package ru.dromran.testtz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMessageDTO {
    private String message;
    private int code;

    public static ResponseEntity<ResponseMessageDTO> getResponseEntity(HttpStatus httpStatus, String message) {
        ResponseMessageDTO responseMessage = new ResponseMessageDTO(message.trim(), httpStatus.value());
        return ResponseEntity.status(httpStatus).body(responseMessage);
    }

    public static ResponseMessageDTO getMessage(String message) {
        return new ResponseMessageDTO(message.trim(), HttpStatus.OK.value());
    }
}
