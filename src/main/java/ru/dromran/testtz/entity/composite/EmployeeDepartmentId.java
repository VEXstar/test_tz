package ru.dromran.testtz.entity.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDepartmentId implements Serializable {

    @Column(name = "employee_department_department_id")
    private Long employeeDepartmentDepartmentId;

    @Column(name = "employee_department_employee_id")
    private Long employeeDepartmentEmployeeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeeDepartmentId that = (EmployeeDepartmentId) o;

        if (!Objects.equals(employeeDepartmentDepartmentId, that.employeeDepartmentDepartmentId)) return false;
        return Objects.equals(employeeDepartmentEmployeeId, that.employeeDepartmentEmployeeId);
    }
}
