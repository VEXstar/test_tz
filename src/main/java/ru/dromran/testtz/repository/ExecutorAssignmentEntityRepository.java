package ru.dromran.testtz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.ExecutorAssignmentEntity;

public interface ExecutorAssignmentEntityRepository extends JpaRepository<ExecutorAssignmentEntity, Long>, JpaSpecificationExecutor<ExecutorAssignmentEntity> {

}