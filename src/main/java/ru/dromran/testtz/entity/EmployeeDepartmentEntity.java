package ru.dromran.testtz.entity;

import lombok.Data;
import ru.dromran.testtz.entity.composite.EmployeeDepartmentId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employee_department")
@IdClass(EmployeeDepartmentId.class)
public class EmployeeDepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "employee_department_department_id", nullable = false)
    private Long employeeDepartmentDepartmentId;

    @Id
    @Column(name = "employee_department_employee_id", nullable = false)
    private Long employeeDepartmentEmployeeId;

}
