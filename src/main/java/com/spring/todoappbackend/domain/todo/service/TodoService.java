package com.spring.todoappbackend.domain.todo.service;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.todo.dto.TodoRequestDto;
import com.spring.todoappbackend.domain.todo.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    // 모든 Todo 조회
    List<TodoResponseDto> getAllTodos();

    // Todo 생성
    TodoResponseDto createTodo(TodoRequestDto todoRequestDto);

    // Todo 수정
    TodoResponseDto updateTodo(Long id, TodoRequestDto todoRequestDto);

    // Todo Complete 수정
    MessageDto updateTodo(Long id);

    // Todo 삭제
    MessageDto deleteTodo(Long id);

    List<TodoResponseDto> searchTodos(String keyword);
}
