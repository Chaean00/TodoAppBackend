package com.spring.todoappbackend.domain.user.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class SignInRequestDto {
    private String uid;
    private String password;
}
