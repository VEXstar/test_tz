package ru.dromran.testtz.entity;

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
@Table(name = "organization")
public class OrganizationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id", nullable = false)
    private Long id;

    @Column(name = "organization_name", nullable = false)
    private String name;

    @Column(name = "organization_physical_address", nullable = false)
    private String physicalAddress;

    @Column(name = "organization_legal_address", nullable = false)
    private String legalAddress;

    @Column(name = "organization_chef")
    private Long chefId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_chef", referencedColumnName = "employee_id", insertable = false, updatable = false)
    private EmployeeEntity chef;

    @OneToMany(mappedBy = "organization")
    private List<DepartmentEntity> departmentEntityList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationEntity that = (OrganizationEntity) o;

        return Objects.equals(id, that.id);
    }
}
