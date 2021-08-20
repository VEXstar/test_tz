package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "executor_assignment")
public class ExecutorAssignmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "executor_user_id", nullable = false)
    private Long executorUserId;

    @Id
    @Column(name = "executor_assignment_id", nullable = false)
    private Long executorAssignmentId;

}
