package ru.dromran.testtz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.AssignmentEntity;

public interface AssignmentEntityRepository extends JpaRepository<AssignmentEntity, Long>, JpaSpecificationExecutor<AssignmentEntity> {

}