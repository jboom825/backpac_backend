package com.backpac.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * com.backpac.security
 * ㄴ CustomAuthenticationFailHandler.java
 * </pre>
 * @date : 2021-04-03 오후 7:21
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 AuthenticationFailureHandler
 *         인증이 실패 하였을때 에러처리를 하기위한 핸들러
 **/
public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {

    /**
     * <pre>
     * onAuthenticationFailure
     * 로그인 실패(아이디/패스워드 실패 등)시 에러처리 후 응답 전송
     * </pre>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException IOException
     * @author 김재범
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        response.setHeader("Character-Encodng", "utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().write(CustomLoginTokenFilter.makeErrorMessage(exception));
    }
}
