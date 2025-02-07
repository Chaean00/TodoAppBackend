package com.spring.todoappbackend.domain.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SignInRequestDto {
    private String uid;
    private String password;
}
