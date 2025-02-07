package com.spring.todoappbackend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    // Jwt 만드는 메서드
    public String createToken(String name, Long id) {
        Instant now = Instant.now();
        Date expirationDate = Date.from(now.plusMillis(Long.parseLong(jwtExpiration)));

        return Jwts.builder()
                .subject(name)
                .claim("id", id)
                .issuedAt(Date.from(now))
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    // 토큰에서 id 추출
    public Long getIdByToken(String token) {
        return getClaimsByToken(token).get("id", Long.class);
    }

    public String getNameByToken(String token) {
        return getClaimsByToken(token).getSubject();
    }

    public boolean validationToken(String token) {
        try {
            getClaimsByToken(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JwtException("토큰 인증 과정에서 오류가 발생했습니다.");
        }
    }

    // 토큰 정보 추출
    private Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
