package com.task.taskmanagement.entity;

import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "title")
    String title;
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    TaskStatus taskStatus;
    @Column(name = "task_priority")
    @Enumerated(EnumType.STRING)
    TaskPriority taskPriority;
    @Column(name = "task_weight")
    String taskWeight;
    @Column(name = "status")
    Boolean status = true;
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        taskStatus = TaskStatus.TODO;
    }
}
