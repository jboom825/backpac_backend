package com.backpac.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * <pre>
 * com.backpac.util
 * ㄴ JwtTokenUtil.java
 * </pre>
 * @date : 2021-04-01 오전 11:59
 * @author : 김재범
 * @version : 1.0.0
 * @desc : JWT(Json web token)을 생성하고 검증하기 위한 공통 유틸
 **/
@Slf4j
@Component
public class JwtTokenUtil {

    public static final String JWT_TOKEN_KEY = "_$$JWT_TOKEN_KEY$$_";

    @Value("${jwt.secure.key}")
    private String secureKey;

    @Value("${jwt.expired.time}")
    private long expired;

    public long getExpired() {
        return expired;
    }

    /**
     * <pre>
     * parseJwtToken
     * JWT 토큰을 검증한다.
     * </pre>
     * @param token String
     * @return boolean
     * @author 김재범
     **/
    public boolean parseJwtToken(String token) {
        //토큰검증
        try{
            Jwts.parser().setSigningKey(secureKey.getBytes(StandardCharsets.UTF_8)).parse(token);
        }catch(ExpiredJwtException e) {
            log.error("ExpiredJwtException occurd.");
            throw e;
        }catch(Exception e) {
            log.error("Exception occurd:" + e.getMessage());
            throw e;
        }
        return true;
    }

    /**
     * <pre>
     * generateJwtToken
     * 사용자 ID로 JWT 토큰을 생성한다.
     * - 토큰 키값과 토큰 유효시간은 application.properties에서 관리
     * </pre>
     * @param id String
     * @return String
     * @author 김재범
     **/
    public String generateJwtToken(String id) {
        //토큰생성
        Claims claims = Jwts.claims().setId(id)
                .setExpiration(new Date(System.currentTimeMillis() + expired));
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secureKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

}
