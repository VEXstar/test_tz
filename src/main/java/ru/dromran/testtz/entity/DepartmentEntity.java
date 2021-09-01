package ru.dromran.testtz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RequiredArgsConstructor
@Entity
@Table(name = "department")
public class DepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String name;

    @Column(name = "department_phone", nullable = false)
    private String phone;

    @Column(name = "department_mail", nullable = false)
    private String mail;

    @Column(name = "department_organization_id")
    private Long organizationId;

    @JsonIgnore
    @Column(name = "department_chef_id")
    private Long chefId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_organization_id", referencedColumnName = "organization_id", insertable = false,
            updatable = false)
    private OrganizationEntity organization;

    @ManyToOne
    @JoinColumn(name = "department_chef_id", referencedColumnName = "employee_id", insertable = false,
            updatable = false)
    private EmployeeEntity chef;

    @OneToMany(mappedBy = "department")
    private List<EmployeeEntity> employeeEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DepartmentEntity that = (DepartmentEntity) o;

        return Objects.equals(id, that.id);
    }
}
