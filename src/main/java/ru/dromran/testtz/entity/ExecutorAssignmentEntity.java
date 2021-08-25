package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.dromran.testtz.entity.composite.ExecutorAssignmentId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "executor_assignment")
@IdClass(ExecutorAssignmentId.class)
public class ExecutorAssignmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "executor_user_id", nullable = false)
    private Long executorUserId;

    @Id
    @Column(name = "executor_assignment_id", nullable = false)
    private Long executorAssignmentId;

    @Column(name = "executor_check")
    private Boolean isDone;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("executorUserId")
    @JoinColumn(name = "executor_user_id", referencedColumnName = "employee_id")
    private EmployeeEntity executor;

    @ManyToOne
    @JsonIgnore
    @MapsId("executorAssignmentId")
    @JoinColumn(name = "executor_assignment_id", referencedColumnName = "assignment_id")
    private AssignmentEntity assignment;


}
