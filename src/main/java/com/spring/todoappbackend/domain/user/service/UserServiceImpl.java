package com.spring.todoappbackend.domain.user.service;

import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.user.entity.User;
import com.spring.todoappbackend.domain.user.repository.UserRepository;
import com.spring.todoappbackend.auth.JwtUtil;
import com.spring.todoappbackend.domain.user.dto.SignInRequestDto;
import com.spring.todoappbackend.domain.user.dto.SignUpRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
            log.error("예외 발생 : ", e);
            throw new RuntimeException("회원 생성 실패.");
        }
    }

    @Override
    public String signIn(SignInRequestDto signInRequestDto, HttpServletResponse response) {

        try {
            User user = userRepository.findByUid(signInRequestDto.getUid()).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

            if (user.getUid().equals(signInRequestDto.getUid()) && passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
                String token = jwtUtil.createToken(user.getName(), user.getId());
                Cookie cookie = new Cookie("access_token", token);
                cookie.setMaxAge(60 * 60 * 24);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);

                return token;
            }
        } catch (Exception e) {
            log.error("예외 발생 : ", e);
            throw new RuntimeException("로그인에 실패했습니다.");
        }
        return null;
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
    }

    @Override
    public MessageDto signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return MessageDto.builder()
                .message("로그아웃에 성공했습니다")
                .isSuccess(true)
                .build();
    }
}
