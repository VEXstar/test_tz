package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "assignment")
public class AssignmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "assignment_author_id")
    private Long assignmentAuthorId;

    @Column(name = "assignment_dead_line", nullable = false)
    private String assignmentDeadLine;

    @Column(name = "assignment_text", nullable = false)
    private String assignmentText;

}
