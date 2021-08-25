package ru.dromran.testtz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentFormDTO {

    @NotEmpty(message = "field name cant be empty")
    private String name;

    @NotEmpty(message = "field phoneNumber cant be empty")
    private String phoneNumber;

    @NotEmpty(message = "field mail cant be empty")
    private String mail;

    @NotEmpty(message = "field organizationId cant be empty")
    private Long organizationId;

    @NotEmpty(message = "field chefId cant be empty")
    private Long chefId;
}
