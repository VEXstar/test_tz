package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
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

    @ManyToOne
    @JoinColumn(name = "organization_chef", referencedColumnName = "employee_id", insertable = false, updatable = false)
    private EmployeeEntity chef;

    @OneToMany(mappedBy = "organization")
    private List<DepartmentEntity> departmentEntityList;

}
