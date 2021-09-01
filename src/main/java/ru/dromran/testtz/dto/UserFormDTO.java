package ru.dromran.testtz.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserFormDTO {

    @NotEmpty(message = "field firstName cant be empty")
    private String firstName;

    @NotEmpty(message = "field lastName cant be empty")
    private String lastName;

    @NotEmpty(message = "field middleName cant be empty")
    private String middleName;

    @NotEmpty(message = "field login cant be empty")
    private String login;


    private String password;

    @NotEmpty(message = "field role cant be empty")
    private String role;

    @NotNull(message = "field postId cant be empty")
    private Long postId;

    @NotNull(message = "field departmentId cant be empty")
    private Long departmentId;


}
