package com.task.taskmanagement.repository;

import com.task.taskmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Integer id);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
    Optional<UserEntity> findByIdAndIsEnabledTrue(Integer id);
    Page<UserEntity> findAllByIsEnabledTrue(Pageable pageable);
}
