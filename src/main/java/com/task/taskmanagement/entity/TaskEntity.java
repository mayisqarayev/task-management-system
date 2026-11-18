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
    @Column(name = "title", nullable = false, unique = true, length = 40)
    String title;
    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    String description;
    @Column(name = "task_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    TaskStatus taskStatus;
    @Column(name = "task_priority", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    TaskPriority taskPriority;
    @Column(name = "task_weight")
    Short taskWeight;
    @Column(name = "status", nullable = false)
    Boolean status;
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if(taskStatus == null) {
            taskStatus = TaskStatus.TODO;
        }

        if(status == null) {
            status = true;
        }

        createdAt = LocalDateTime.now();
    }
}
