package com.spring.todoappbackend.domain.user.service;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.user.entity.User;
import com.spring.todoappbackend.domain.user.repository.UserRepository;
import com.spring.todoappbackend.auth.JwtUtil;
import com.spring.todoappbackend.domain.user.dto.SignInRequestDto;
import com.spring.todoappbackend.domain.user.dto.SignUpRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public MessageDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.findByUid(signUpRequestDto.getUid()).isPresent()) {
            throw new RuntimeException("이미 사용 중인 ID입니다.");
        }

        try {
            String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

            userRepository.save(User.builder()
                    .uid(signUpRequestDto.getUid())
                    .name(signUpRequestDto.getName())
                    .password(encodedPassword)
                    .build());

            return MessageDto.builder()
                    .isSuccess(true)
                    .message("회원가입에 성공했습니다.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("회원 생성 실패.");
        }
    }

    @Override
    public String signIn(SignInRequestDto signInRequestDto) {

        try {
            User user = userRepository.findByUid(signInRequestDto.getUid()).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

            if (user.getUid().equals(signInRequestDto.getUid()) && passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
                return jwtUtil.createToken(user.getName(), user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("로그인에 실패했습니다.");
        }
        return null;
    }
}
