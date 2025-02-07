package com.spring.todoappbackend.domain.todo.service;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.todo.dto.TodoRequestDto;
import com.spring.todoappbackend.domain.todo.dto.TodoResponseDto;
import com.spring.todoappbackend.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoResponseDto> getAllTodos() {
        return null;
    }

    @Override
    public TodoResponseDto getTodoById(Long id) {
        return null;
    }

    @Override
    public MessageDto createTodo(TodoRequestDto todoRequestDto) {
        return null;
    }

    @Override
    public MessageDto updateTodo(Long id, TodoRequestDto todoRequestDto) {
        return null;
    }
}
