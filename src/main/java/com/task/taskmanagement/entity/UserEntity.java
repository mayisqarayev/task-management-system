package com.task.taskmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Integer id;
    @Column(name = "username", unique = true, nullable = false, length = 30)
    String username;
    @Column(name = "password", nullable = false, length = 255)
    String password;
    @Column(name = "email", nullable = false, unique = true, length = 40)
    String email;
    @Column(name = "first_name", length = 40)
    String firstName;
    @Column(name = "last_name", length = 40)
    String lastName;
    @Column(name = "is_enabled", nullable = false)
    boolean isEnabled = true;

}
