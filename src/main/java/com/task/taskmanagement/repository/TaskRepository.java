package com.task.taskmanagement.repository;

import com.task.taskmanagement.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer>,
        JpaSpecificationExecutor<TaskEntity> {
    boolean existsByTitleIgnoreCase(String title);
    boolean existsByTitleAndIdNot(String title, Integer id);
}
