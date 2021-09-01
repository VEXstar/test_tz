package ru.dromran.testtz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.PostEntity;

public interface EmployeeEntityRepository extends
        JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {

    EmployeeEntity findEmployeeEntitiesByLogin(String login);

    Page<EmployeeEntity>
    findEmployeeEntitiesByFirstNameContainingAndLastNameContainingAndMiddleNameContainingAndLoginAndPost(
            String firstName,
            String lastName,
            String middleName,
            String login,
            PostEntity post,
            Pageable pageable);

}