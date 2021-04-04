package com.backpac.exception;

/**
 * <pre>
 * com.backpac.exception
 * ㄴ UserNotFoundException.java
 * </pre>
 * @date : 2021-04-01 오후 1:32
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 존재하지 않는 사용자 Exception
 **/
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }
}
