package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.dromran.testtz.entity.composite.EmployeeDepartmentId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RequiredArgsConstructor
@Entity
@Table(name = "employee_department")
public class EmployeeDepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    EmployeeDepartmentId employeeDepartmentId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeeDepartmentEntity that = (EmployeeDepartmentEntity) o;

        if (!Objects.equals(employeeDepartmentId.getEmployeeDepartmentDepartmentId(),
                that.employeeDepartmentId.getEmployeeDepartmentDepartmentId()))
            return false;
        return Objects.equals(employeeDepartmentId.getEmployeeDepartmentEmployeeId(),
                that.employeeDepartmentId.getEmployeeDepartmentEmployeeId());
    }
}
