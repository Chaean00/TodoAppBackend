package com.spring.todoappbackend.domain.user.dto;

import com.spring.todoappbackend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequestDto {
    private String uid;
    private String password;
    private String name;

    public User toEntity() {
        return User.builder()
                .uid(uid)
                .password(password)
                .name(name)
                .build();
    }
}
