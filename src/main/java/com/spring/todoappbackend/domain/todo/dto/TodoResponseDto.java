package com.spring.todoappbackend.domain.todo.dto;

import com.spring.todoappbackend.domain.todo.enums.Category;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String title;
    private boolean completed;
    private Category category;
    private LocalDate createdAt;
}
