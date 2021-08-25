package ru.dromran.testtz.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssignmentFormDTO {

    @NotEmpty(message = "field type cant be empty")
    private String type;

    private List<Long> executors;

    @NotNull(message = "field deadLine cant be empty")
    private LocalDateTime deadLine;

    @NotEmpty(message = "field about cant be empty")
    private String about;

}
