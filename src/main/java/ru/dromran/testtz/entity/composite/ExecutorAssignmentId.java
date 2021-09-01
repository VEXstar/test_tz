package ru.dromran.testtz.entity.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExecutorAssignmentId implements Serializable {


    @Column(name = "executor_user_id")
    private Long executorUserId;

    @Column(name = "executor_assignment_id")
    private Long executorAssignmentId;
}
