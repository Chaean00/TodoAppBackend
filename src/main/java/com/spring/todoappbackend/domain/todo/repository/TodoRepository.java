package com.spring.todoappbackend.domain.todo.repository;

import com.spring.todoappbackend.domain.todo.entity.Todo;
import com.spring.todoappbackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUser(User user);

    List<Todo> findByTitleContainsAndUser(String keyword, User user);
}
