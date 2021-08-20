package ru.dromran.testtz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dromran.testtz.entity.PostEntity;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {

}