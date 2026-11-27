package com.task.taskmanagement.repository;

import com.task.taskmanagement.entity.TaskEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer>,
        JpaSpecificationExecutor<TaskEntity> {
    boolean existsByTitleIgnoreCase(String title);
    boolean existsByTitleIgnoreCaseAndIdNot(String title, Integer id);
    Optional<TaskEntity> findByIdAndStatusTrue(int id);
}
