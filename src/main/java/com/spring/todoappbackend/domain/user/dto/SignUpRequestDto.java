package com.spring.todoappbackend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequestDto {
    private String uid;
    private String password;
    private String name;
}
