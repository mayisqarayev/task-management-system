package com.task.taskmanagement.specification;

import com.task.taskmanagement.entity.TaskEntity;
import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;
import com.task.taskmanagement.service.UserService;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    private TaskSpecification() {
    }


    public static Specification<TaskEntity> isActive() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isTrue(root.get("status"));
        };
    }


    public static Specification<TaskEntity> hasTaskStatus(TaskStatus taskStatus) {
        return (root, query, criteriaBuilder) -> {
            if(taskStatus == null) return null;

            return criteriaBuilder.equal(root.get("taskStatus"), taskStatus);
        };
    }

    public static Specification<TaskEntity> hasTaskPriority(TaskPriority taskPriority) {
        return (root, query, criteriaBuilder) -> {
            if(taskPriority == null) return null;

            return criteriaBuilder.equal(root.get("taskPriority"), taskPriority);
        };
    }
}
