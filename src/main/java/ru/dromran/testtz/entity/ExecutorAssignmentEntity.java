package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.dromran.testtz.entity.composite.ExecutorAssignmentId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RequiredArgsConstructor
@Entity
@Table(name = "executor_assignment")
public class ExecutorAssignmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    ExecutorAssignmentId executorAssignmentId;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExecutorAssignmentEntity that = (ExecutorAssignmentEntity) o;

        if (!Objects.equals(executorAssignmentId.getExecutorUserId(),
                that.executorAssignmentId.getExecutorAssignmentId()))
            return false;
        return Objects.equals(executorAssignmentId.getExecutorAssignmentId(),
                that.executorAssignmentId.getExecutorAssignmentId());
    }
}
