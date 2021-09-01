package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RequiredArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long id;

    @Column(name = "employee_first_name", nullable = false)
    private String firstName;

    @Column(name = "employee_last_name", nullable = false)
    private String lastName;

    @Column(name = "employee_middle_name", nullable = false)
    private String middleName;

    @Column(name = "employee_login", nullable = false)
    private String login;

    @Column(name = "employee_role", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "employee_post", nullable = false)
    private PostEntity post;

    @Column(name = "employee_password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToOne
    @JsonIgnore
    @JoinTable(name = "employee_department", joinColumns = @JoinColumn(name = "employee_department_employee_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_department_department_id"))
    private DepartmentEntity department;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeeEntity employee = (EmployeeEntity) o;

        return Objects.equals(id, employee.id);
    }
}
