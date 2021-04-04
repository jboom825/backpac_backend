package com.backpac.security;

import com.backpac.dto.ErrorResponseDto;
import com.backpac.dto.LoginUserDto;
import com.backpac.exception.InvalidLoginParameterException;
import com.backpac.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * com.backpac.security
 * ㄴ CustomLoginTokenFilter.java
 * </pre>
 * @date : 2021-04-01 오후 1:35
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 UsernamePasswordAuthenticationFilter 를 상속받은 Custom Filter
 *         ID / Password 기반의 로그인 정보를 받아 인증을 처리하기 위한 인증 필터
 **/
@Slf4j
public class CustomLoginTokenFilter extends UsernamePasswordAuthenticationFilter {

    public CustomLoginTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * <pre>
     * attemptAuthentication
     * 로그인 요청 정보로 UsernamePasswordAuthenticationToken 을 생성하여 AuthenticationManager에 인증요청
     * </pre>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return org.springframework.security.core.Authentication
     * @throws AuthenticationException 인증오류
     * @author 김재범
     **/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authRequest;
        try {
            final LoginUserDto user = new ObjectMapper().readValue(request.getInputStream(), LoginUserDto.class);
            authRequest = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidLoginParameterException("invalid login parameter. ",e);
        }
        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * <pre>
     * doFilter
     * 필터처리중 의도하지 않은 RuntimeException이 발생시 에러처리
     * </pre>
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     * @return void
     * @throws IOException  IO오류
     * @throws ServletException 서블릿오류
     * @author 김재범
     **/
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            super.doFilter(request, response, chain);
        }catch(RuntimeException ex) {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(makeErrorMessage(ex));
        }
    }

    /**
     * <pre>
     * makeErrorMessage
     * AuthenticationFilter 전반에 발생하는 에러들에 대해 에러메시지를 만들어주는 공통 함수
     * </pre>
     * @param ex Exception
     * @return String
     * @author 김재범
     **/
    protected static String makeErrorMessage(Exception ex) {
        String errorMessage;
        try {
            if (ex instanceof BadCredentialsException) {
                errorMessage = new ObjectMapper().writeValueAsString(new ErrorResponseDto("400", "비밀번호가 틀렸습니다."));
            } else if (ex instanceof UserNotFoundException) {
                errorMessage = new ObjectMapper().writeValueAsString(new ErrorResponseDto("400", "존재하지 않는 사용자입니다."));
            } else {
                ErrorResponseDto errorResponseDto = ErrorResponseDto.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
                while(ex.getCause() != null) {
                    Throwable nex = ex.getCause();
                    errorResponseDto = ErrorResponseDto.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), nex.getMessage());
                }
                errorMessage = new ObjectMapper().writeValueAsString(errorResponseDto);
            }
        }catch(Exception e){
            errorMessage = "{\"errorCode\": \"400\", \"errorMessage\":\"" + e.getMessage() + "\" }";
        }

        return errorMessage;
    }
}
