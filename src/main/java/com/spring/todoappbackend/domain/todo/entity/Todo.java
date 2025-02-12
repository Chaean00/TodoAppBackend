package com.spring.todoappbackend.domain.todo.entity;

import com.spring.todoappbackend.common.BaseEntity;
import com.spring.todoappbackend.domain.todo.enums.Category;
import com.spring.todoappbackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "todo", indexes = @Index(name = "idx_title", columnList = "title"))
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 50)
    private String title;

    @Setter
    @Column(nullable = false)
    @Builder.Default
    private boolean completed = false;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
