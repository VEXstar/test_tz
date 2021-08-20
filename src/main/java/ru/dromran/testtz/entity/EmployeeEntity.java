package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employee")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "employee_first_name", nullable = false)
    private String employeeFirstName;

    @Column(name = "employee_last_name", nullable = false)
    private String employeeLastName;

    @Column(name = "employee_middle_name", nullable = false)
    private String employeeMiddleName;

    @Column(name = "employee_post", nullable = false)
    private Long employeePost;

    @Column(name = "employee_password", nullable = false)
    private String employeePassword;

}
