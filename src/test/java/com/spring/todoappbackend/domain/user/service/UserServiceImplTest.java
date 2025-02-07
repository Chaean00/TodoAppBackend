package com.spring.todoappbackend.domain.user.service;

import com.spring.todoappbackend.auth.JwtUtil;
import com.spring.todoappbackend.common.MessageDto;
import com.spring.todoappbackend.domain.user.dto.SignInRequestDto;
import com.spring.todoappbackend.domain.user.dto.SignUpRequestDto;
import com.spring.todoappbackend.domain.user.entity.User;
import com.spring.todoappbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("회원가입 테스트 - 성공")
    public void signUpSuccessTest() throws Exception{
        //given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .uid("testUid")
                .name("김정택")
                .password("testPassword")
                .build();
        //when
        MessageDto messageDto = userServiceImpl.signUp(signUpRequestDto);

        //then
        assertNotNull(messageDto, "Not Null");
        assertTrue(messageDto.isSuccess(), "회원가입 성공 : True");
        assertEquals(messageDto.getMessage(), "회원가입에 성공했습니다.");
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패")
    public void signUpExceptionTest() throws Exception{
        //given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .uid("testUid")
                .name("김정택")
                .password("testPassword")
                .build();

        //when
        when(userRepository.findByUid("testUid"))
                .thenReturn(Optional.of(User.builder().uid("testUid").build()));

        RuntimeException e = assertThrows(RuntimeException.class, () -> userServiceImpl.signUp(signUpRequestDto));
        //then
        assertEquals("이미 사용 중인 ID입니다.", e.getMessage());
    }

    @Test
    @DisplayName("로그인 테스트 - 성공")
    public void signInSuccessTest() throws Exception{
        //given
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .uid("testUid")
                .password("testPassword")
                .build();

        User user = User.builder()
                .id(1L)
                .uid("testUid")
                .name("김정택")
                .password("encryptedPassword")
                .build();
        //when
        when(userRepository.findByUid("testUid")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("testPassword", "encryptedPassword")).thenReturn(true);
        when(jwtUtil.createToken("김정택", 1L)).thenReturn("jwt");

        String token = userServiceImpl.signIn(signInRequestDto);
        //then
        assertNotNull(token, "token Not Null");
        assertEquals(token, "jwt");
    }

    @Test
    @DisplayName("로그인 테스트 - 실패")
    public void signInFailTest() throws Exception{
        //given
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .uid("testUid")
                .password("testPassword")
                .build();
        //when
        when(userRepository.findByUid("testUid")).thenReturn(Optional.empty());

        RuntimeException e = assertThrows(RuntimeException.class, () -> userServiceImpl.signIn(signInRequestDto));
        //then

        assertEquals("로그인에 실패했습니다.", e.getMessage());
    }

}