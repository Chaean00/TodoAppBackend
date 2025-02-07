package com.spring.todoappbackend.domain.user.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String uid;
    private String password;
    private String name;
}
