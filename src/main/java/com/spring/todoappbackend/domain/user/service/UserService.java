package com.spring.todoappbackend.domain.user.service;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.user.dto.SignInRequestDto;
import com.spring.todoappbackend.domain.user.dto.SignUpRequestDto;
import com.spring.todoappbackend.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface UserService {

    // 회원 생성 (회원가입)
    MessageDto signUp(SignUpRequestDto signUpRequestDto);

    // 로그인
    String signIn(SignInRequestDto signInRequestDto, HttpServletResponse response);

    // 유저 조회
    User findUser(Long id);

    MessageDto signOut(HttpServletResponse response);
}
