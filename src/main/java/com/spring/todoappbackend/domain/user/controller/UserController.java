package com.spring.todoappbackend.domain.user.controller;

import com.spring.todoappbackend.domain.user.dto.SignInRequestDto;
import com.spring.todoappbackend.domain.user.dto.SignUpRequestDto;
import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<MessageDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        return ResponseEntity.ok(userService.signUp(signUpRequestDto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(userService.signIn(signInRequestDto));
    }
}
