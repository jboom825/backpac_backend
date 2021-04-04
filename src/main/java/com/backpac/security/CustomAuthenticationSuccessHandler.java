package com.backpac.security;

import com.backpac.dto.CommonDto;
import com.backpac.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * com.backpac.security
 * ㄴ CustomAuthenticationSuccessHandler.java
 * </pre>
 * @date : 2021-04-03 오후 6:30
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 AuthenticationFailureHandler
 *         인증이 성공하였을 경우 처리를 위한 핸들러
 **/
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtTokenUtil jwtUtil;

    /**
     * <pre>
     * onAuthenticationSuccess
     * 사용자 id를 토대로 JWT 토큰을 생성하여 응답헤더 "Authorization" 필드에 내려준다.
     * </pre>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication Authentication
     * @throws  IOException IOException
     * @author 김재범
     **/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String jwtToken = jwtUtil.generateJwtToken(token.getName());
        response.addHeader("Authorization", jwtToken);
        response.setHeader("Character-Encodng", "utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new CommonDto("0", "로그인이 성공하였습니다.")));
    }
}
