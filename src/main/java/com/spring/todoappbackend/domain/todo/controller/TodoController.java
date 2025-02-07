package com.spring.todoappbackend.domain.todo.controller;

import com.spring.todoappbackend.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
}
