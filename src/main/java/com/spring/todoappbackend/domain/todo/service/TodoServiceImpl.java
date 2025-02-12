package com.spring.todoappbackend.domain.todo.service;

import com.spring.todoappbackend.auth.AuthUtils;
import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.todo.dto.TodoRequestDto;
import com.spring.todoappbackend.domain.todo.dto.TodoResponseDto;
import com.spring.todoappbackend.domain.todo.entity.Todo;
import com.spring.todoappbackend.domain.todo.repository.TodoRepository;
import com.spring.todoappbackend.domain.user.entity.User;
import com.spring.todoappbackend.domain.user.repository.UserRepository;
import com.spring.todoappbackend.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;

    @Override
    public List<TodoResponseDto> getAllTodos() {
        Long id = AuthUtils.getUserId();

        try {
            User user = userService.findUser(id);
            List<Todo> todoLists = todoRepository.findAllByUser(user);

            return todoLists.stream()
                    .map(todo -> TodoResponseDto.builder()
                            .id(todo.getId())
                            .title(todo.getTitle())
                            .completed(todo.isCompleted())
                            .category(todo.getCategory())
                            .createdAt(todo.getCreatedAt().toLocalDate())
                            .build())
                    .toList();
        } catch (Exception e) {
            log.error("예외 발생 : " + e);
            throw new RuntimeException("TodoList 불러오기에 실패했습니다.");
        }
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        Long userId = AuthUtils.getUserId();

        try {
            User user = userService.findUser(userId);

            Todo todo = todoRepository.save(todoRequestDto.toEntity(user));

            return TodoResponseDto.builder()
                    .id(todo.getId())
                    .createdAt(todo.getCreatedAt().toLocalDate())
                    .completed(todo.isCompleted())
                    .title(todo.getTitle())
                    .category(todo.getCategory())
                    .build();
        } catch (Exception e) {
            log.error("예외 발생 : " + e);
            throw new RuntimeException("Todo 생성에 실패했습니다.");
        }
    }

    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto todoRequestDto) {
        try {
            Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Todo입니다."));

            todo.setCategory(todoRequestDto.getCategory());
            todo.setTitle(todoRequestDto.getTitle());

            Todo updateTodo = todoRepository.save(todo);
            return TodoResponseDto.builder()
                    .id(updateTodo.getId())
                    .category(updateTodo.getCategory())
                    .title(updateTodo.getTitle())
                    .completed(updateTodo.isCompleted())
                    .createdAt(updateTodo.getUpdatedAt().toLocalDate())
                    .build();
        } catch (Exception e) {
            log.error("예외 발생 : " + e);
            throw new RuntimeException("Todo 수정에 실패했습니다.");
        }
    }

    @Override
    public MessageDto updateTodo(Long id) {
        try {
            Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Todo입니다."));

            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);

            return MessageDto.builder()
                    .isSuccess(true)
                    .message("수정 완료")
                    .build();
        } catch (Exception e) {
            log.error("예외 발생 : " + e);
            throw new RuntimeException("Todo 수정에 실패했습니다.");
        }
    }

    @Override
    public MessageDto deleteTodo(Long id) {
        try {
            Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지않는 Todo입니다."));
            todoRepository.delete(todo);

            return MessageDto.builder()
                    .isSuccess(true)
                    .message("삭제 완료.")
                    .build();
        } catch (Exception e) {
            log.error("예외 발생 : ", e);
            throw new RuntimeException("Todo 삭제에 실패했습니다.");
        }
    }

    @Override
    public List<TodoResponseDto> searchTodos(String keyword) {
        Long id = AuthUtils.getUserId();
        try {
            User user = userService.findUser(id);

            List<Todo> todos = todoRepository.findByTitleContainsAndUser(keyword, user);

            return todos.stream()
                    .map(todo -> TodoResponseDto.builder()
                            .id(todo.getId())
                            .title(todo.getTitle())
                            .completed(todo.isCompleted())
                            .category(todo.getCategory())
                            .createdAt(todo.getCreatedAt().toLocalDate())
                            .build())
                    .toList();
        } catch (Exception e) {
            log.error("예외 발생 : ", e);
            throw new RuntimeException("Todo 검색에 실패했습니다.");
        }
    }
}
