package ru.dromran.testtz.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "organization")
public class OrganizationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "organization_physical_address", nullable = false)
    private String organizationPhysicalAddress;

    @Column(name = "organization_legal_address", nullable = false)
    private String organizationLegalAddress;

    @Column(name = "organization_chef")
    private Long organizationChef;

}
