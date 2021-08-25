package ru.dromran.testtz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.EmployeeDepartmentEntity;
import ru.dromran.testtz.entity.composite.EmployeeDepartmentId;

public interface EmployeeDepartmentEntityRepository extends
        JpaRepository<EmployeeDepartmentEntity, EmployeeDepartmentId>,
        JpaSpecificationExecutor<EmployeeDepartmentEntity> {

}