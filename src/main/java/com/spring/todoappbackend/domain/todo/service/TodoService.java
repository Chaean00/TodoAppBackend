package com.spring.todoappbackend.domain.todo.service;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.todo.dto.TodoRequestDto;
import com.spring.todoappbackend.domain.todo.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    // 모든 Todo 조회
    List<TodoResponseDto> getAllTodos();

    // 특정 Todo 조회
    TodoResponseDto getTodoById(Long id);

    MessageDto createTodo(TodoRequestDto todoRequestDto);

    MessageDto updateTodo(Long id, TodoRequestDto todoRequestDto);
}
