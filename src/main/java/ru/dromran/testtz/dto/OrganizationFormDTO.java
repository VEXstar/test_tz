package ru.dromran.testtz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationFormDTO {

    @NotEmpty(message = "field name cant be empty")
    private String name;

    @NotEmpty(message = "field physicalAddress cant be empty")
    private String physicalAddress;

    @NotEmpty(message = "field legalAddress cant be empty")
    private String legalAddress;

    @NotNull(message = "field chefUser cant be empty")
    private Long chefUser;
}
