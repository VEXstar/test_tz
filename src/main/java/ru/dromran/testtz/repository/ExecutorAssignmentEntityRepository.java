package ru.dromran.testtz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.ExecutorAssignmentEntity;
import ru.dromran.testtz.entity.composite.ExecutorAssignmentId;

public interface ExecutorAssignmentEntityRepository extends
        JpaRepository<ExecutorAssignmentEntity, ExecutorAssignmentId>,
        JpaSpecificationExecutor<ExecutorAssignmentEntity> {

}