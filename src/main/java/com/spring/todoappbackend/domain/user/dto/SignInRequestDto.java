package com.spring.todoappbackend.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class SignInRequestDto {
    @NotNull(message = "아이디는 필수 항목입니다.")
    private String uid;
    @NotNull(message = "패스워드는 필수 항목입니다.")
    private String password;
}
