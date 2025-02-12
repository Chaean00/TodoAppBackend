package com.spring.todoappbackend.domain.user.controller;

import com.spring.todoappbackend.domain.user.dto.SignInRequestDto;
import com.spring.todoappbackend.domain.user.dto.SignUpRequestDto;
import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "User Auth API", description = "사용자 인증 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<MessageDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        return ResponseEntity.ok(userService.signUp(signUpRequestDto));
    }

    @Operation(summary = "로그인", description = "사용자가 로그인하면 JWT 토큰을 반환합니다.")
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(userService.signIn(signInRequestDto, response));
    }

    @Operation(summary = "로그아웃", description = "로그아웃 시 사용자의 JWT 토큰을 제거합니다.")
    @PostMapping("/signout")
    public ResponseEntity<MessageDto> signOut(HttpServletResponse response) {
        return ResponseEntity.ok(userService.signOut(response));
    }
}
