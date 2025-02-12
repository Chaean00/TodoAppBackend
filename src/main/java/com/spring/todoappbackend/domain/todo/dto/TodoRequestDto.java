package com.spring.todoappbackend.domain.todo.dto;

import com.spring.todoappbackend.domain.todo.entity.Todo;
import com.spring.todoappbackend.domain.todo.enums.Category;
import com.spring.todoappbackend.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class TodoRequestDto {
    @NotNull(message = "제목은 필수 항목입니다.")
    @Size(max = 50, message = "할일명은 50자 이하로 작성해야 합니다.")
    private String title;
    @NotNull(message = "카테고리는 필수 항목입니다.")
    private Category category;

    public Todo toEntity(User user) {
        return Todo.builder()
                .title(title)
                .category(category)
                .user(user)
                .build();
    }
}
