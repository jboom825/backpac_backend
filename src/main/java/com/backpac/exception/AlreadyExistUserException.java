package com.backpac.exception;

/**
 * <pre>
 * com.backpac.exception
 * ㄴ UserNotFoundException.java
 * </pre>
 * @date : 2021-04-01 오후 1:32
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 사용자 생성시 이미 존재하는 사용자 에러
 **/
public class AlreadyExistUserException extends RuntimeException {

    private final String ERROR_MESSAGE = "이미 존재하는 사용자입니다.";

    public AlreadyExistUserException() {
        new AlreadyExistUserException(ERROR_MESSAGE);
    }

    public AlreadyExistUserException(String message) {
        super(message);
    }
}
