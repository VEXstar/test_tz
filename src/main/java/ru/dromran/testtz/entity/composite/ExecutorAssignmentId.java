package ru.dromran.testtz.entity.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExecutorAssignmentId implements Serializable {

    private Long executorUserId;

    private Long executorAssignmentId;
}
