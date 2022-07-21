package com.t2009m1.spring.security.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.t2009m1.spring.security.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    @Test
    public void testGenerateToken() {
       String accessToken = JwtUtil.generateToken("1234", "USER", "T2009M1", 60 * 60 * 10000 * 7 * 24);
       System.out.println(accessToken);
       DecodedJWT decodedJWT = JwtUtil.getVerifier().verify(accessToken);
       System.out.println(decodedJWT.getSubject());
       System.out.println(decodedJWT.getIssuer());
       System.out.println(decodedJWT.getExpiresAt());

        Account account =  Account.builder()
                .username("tien")
                .passwordHash("123456")
                .role(1)
                .build();


    }

}