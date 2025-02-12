package com.spring.todoappbackend.domain.todo.dto;

import com.spring.todoappbackend.domain.todo.entity.Todo;
import com.spring.todoappbackend.domain.todo.enums.Category;
import com.spring.todoappbackend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class TodoRequestDto {
    private String title;
    private Category category;

    public Todo toEntity(User user) {
        return Todo.builder()
                .title(title)
                .category(category)
                .user(user)
                .build();
    }
}
