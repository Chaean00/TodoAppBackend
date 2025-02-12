package com.spring.todoappbackend.domain.todo.controller;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.todo.dto.TodoRequestDto;
import com.spring.todoappbackend.domain.todo.dto.TodoResponseDto;
import com.spring.todoappbackend.domain.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Tag(name = "Todo API", description = "Todo 관련 API")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "모든 Todo 조회", description = "등록된 모든 Todo 리스트를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @Operation(summary = "Todo 생성", description = "새로운 Todo를 생성합니다.")
    @PostMapping("")
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todoRequestDto) {
        return ResponseEntity.ok(todoService.createTodo(todoRequestDto));
    }

    @Operation(summary = "Todo 수정", description = "기존 Todo의 Category 및 Title을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto todoRequestDto) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoRequestDto));
    }

    @Operation(summary = "Todo 완료 상태 수정", description = "기존 Todo의 완료 상태를 수정합니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<MessageDto> updateCompleteTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.updateTodo(id));
    }

    @Operation(summary = "Todo 삭제", description = "Todo를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.deleteTodo(id));
    }

    @Operation(summary = "Todo 검색", description = "키워드를 포함하는 Todo를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<TodoResponseDto>> searchTodos(@RequestParam String keyword) {
        return ResponseEntity.ok(todoService.searchTodos(keyword));
    }
}
