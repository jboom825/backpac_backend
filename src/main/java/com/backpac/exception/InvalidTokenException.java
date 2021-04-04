package com.backpac.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <pre>
 * com.backpac.exception
 * ㄴ InvalidTokenException.java
 * </pre>
 * @date : 2021-04-01 오후 1:11
 * @author : 김재범
 * @version : 1.0.0
 * @desc : JWT 토큰 검증 실패 Exception
 **/
public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
