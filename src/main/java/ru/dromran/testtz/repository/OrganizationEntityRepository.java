package ru.dromran.testtz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.OrganizationEntity;

public interface OrganizationEntityRepository extends JpaRepository<OrganizationEntity, Long>, JpaSpecificationExecutor<OrganizationEntity> {

}