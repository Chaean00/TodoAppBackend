package com.spring.todoappbackend.domain.todo.repository;

import com.spring.todoappbackend.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
