package ru.dromran.testtz.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDTO {

    @NotEmpty(message = "field login cant be empty")
    private String login;

    @NotEmpty(message = "field password cant be empty")
    private String password;

}
