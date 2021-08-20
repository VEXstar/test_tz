package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "department")
public class DepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @Column(name = "department_phone", nullable = false)
    private String departmentPhone;

    @Column(name = "department_mail", nullable = false)
    private String departmentMail;

    @Column(name = "department_organization_id", nullable = false)
    private Long departmentOrganizationId;

    @Column(name = "department_chef_id")
    private Long departmentChefId;

}
