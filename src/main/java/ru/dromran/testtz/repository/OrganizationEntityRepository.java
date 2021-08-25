package ru.dromran.testtz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.OrganizationEntity;

import java.util.List;

public interface OrganizationEntityRepository extends
        JpaRepository<OrganizationEntity, Long>, JpaSpecificationExecutor<OrganizationEntity> {

    OrganizationEntity getOrganizationEntityByName(String name);

    Page<OrganizationEntity>
    findOrganizationEntitiesByNameContainingAndPhysicalAddressContainingAndLegalAddressContainingAndChef(
            String nameTerm,
            String physAddress,
            String legalAddress,
            EmployeeEntity chef,
            Pageable pageable);

    List<OrganizationEntity> findOrganizationEntitiesByChef(EmployeeEntity employee);
}