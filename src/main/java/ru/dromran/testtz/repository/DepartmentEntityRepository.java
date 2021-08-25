package ru.dromran.testtz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.DepartmentEntity;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.OrganizationEntity;

public interface DepartmentEntityRepository extends
        JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor<DepartmentEntity> {


    Page<DepartmentEntity>
    findDepartmentEntitiesByNameContainingAndOrganizationAndChef(String name,
                                                                 OrganizationEntity organization,
                                                                 EmployeeEntity employee,
                                                                 Pageable pageable);

}