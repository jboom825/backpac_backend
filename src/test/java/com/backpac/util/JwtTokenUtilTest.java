package com.backpac.util;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenUtilTest {

    @Autowired
    JwtTokenUtil jwtUtil;

    @Test
    void name() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmbGVhODE1IiwiZXhwIjoxNjE3MzQyNTA1fQ.XXgOAGArQZEfQhSTxqIQv4-VMOp_wkLgPWsyB5nS-QpQvcyvoRFtfrgxIOOLXGH0ep1KuESA2Iz9Y42QP2RNDQ";
        System.out.println(jwtUtil.parseJwtToken(token));
    }
}