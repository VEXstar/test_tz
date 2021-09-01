package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RequiredArgsConstructor
@Entity
@Table(name = "assignment")
public class AssignmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id", nullable = false)
    private Long id;

    @Column(name = "assignment_dead_line", nullable = false)
    private LocalDateTime assignmentDeadLine;

    @Column(name = "assignment_text", nullable = false)
    private String assignmentText;

    @Column(name = "assignment_type", nullable = false)
    private String type;

    @Column(name = "assignment_control", nullable = false)
    private Boolean isControl;

    @Column(name = "assignment_execution", nullable = false)
    private Boolean isExecution;

    @Column(name = "assignment_done", nullable = false)
    private Boolean isDone;

    @JsonIgnore
    @Column(name = "assignment_author_id", nullable = false)
    private Long authorId;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.EAGER)
    private List<ExecutorAssignmentEntity> executorAssignmentEntities;

    @ManyToOne
    @JoinColumn(name = "assignment_author_id",
            referencedColumnName = "employee_id",
            insertable = false, updatable = false)
    private EmployeeEntity author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AssignmentEntity that = (AssignmentEntity) o;

        return Objects.equals(id, that.id);
    }
}
