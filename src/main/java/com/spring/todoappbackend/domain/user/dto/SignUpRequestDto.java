package com.spring.todoappbackend.domain.user.dto;

import com.spring.todoappbackend.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequestDto {
    @NotNull(message = "아이디는 필수 항목입니다.")
    private String uid;
    @NotNull(message = "패스워드는 필수 항목입니다.")
    private String password;
    @NotNull(message = "이름은 필수 항목입니다.")
    private String name;
}
