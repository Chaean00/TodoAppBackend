package com.spring.todoappbackend.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("토큰 생성 테스트")
    public void createTokenTest() throws Exception{
        //given
        String name = "김정택";
        Long id = 15L;
        //when
        String token = jwtUtil.createToken(name, id);

        //then
        String tokenName = jwtUtil.getNameByToken(token);
        Long tokenId = jwtUtil.getIdByToken(token);


        assertNotNull(token, "Not Null");
        assertEquals(name, tokenName, "Name Equals TokenName");
        assertEquals(id, tokenId, "Id Equals TokenId");

        assertTrue(jwtUtil.validationToken(token));
    }

}