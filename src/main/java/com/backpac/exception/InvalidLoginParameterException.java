package com.backpac.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <pre>
 * com.backpac.exception
 * ㄴ InvalidLoginParameterException.java
 * </pre>
 * @date : 2021-04-01 오후 1:09
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 로그인 처리중(UsernamePasswordAuthenticationFilter) 로그인 파라미터 검증 실패 Exception
 **/
public class InvalidLoginParameterException extends AuthenticationException {

    public InvalidLoginParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
