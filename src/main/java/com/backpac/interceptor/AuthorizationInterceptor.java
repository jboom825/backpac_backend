package com.backpac.interceptor;

import com.backpac.annotation.RequiredAuthorization;
import com.backpac.exception.InvalidTokenException;
import com.backpac.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * com.backpac.interceptor
 * ㄴ AuthorizationInterceptor.java
 * </pre>
 * @date : 2021-04-03 오전 11:26
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 로그인이 필요한 API 접근시 인증을 위한 인터셉터
 **/
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * <pre>
     * preHandle
     * 로그인이 필요한 API 요청시 (RequiredAuthorization 어노테이션 값이 true) 토큰 검증 처리
     * - JWT 토큰 검증
     * - Redis 서버에 토큰 값이 있는지 확인(로그아웃 토큰)
     * </pre>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @return boolean
     * @throws InvalidTokenException 유효하지않은토큰에러
     * @author 김재범
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InvalidTokenException {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiredAuthorization annotation = handlerMethod.getMethodAnnotation(RequiredAuthorization.class);
            if(annotation != null && annotation.value()) {
                try{
                    if(request.getHeader("Authorization") == null) throw new InvalidTokenException("Empty Authorization Token.");
                    String token = request.getHeader("Authorization").replaceFirst("Bearer ", "");
                    jwtTokenUtil.parseJwtToken(token);
                    ValueOperations<String, String> values =  redisTemplate.opsForValue();
                    if(values.get(token) != null) throw new InvalidTokenException("Already Expired Token.");
                    request.setAttribute(JwtTokenUtil.JWT_TOKEN_KEY, token);
                }catch(ExpiredJwtException e) {
                    throw new InvalidTokenException("Invalid Token.", e);
                }
            }
        }
        return true;
    }
}
