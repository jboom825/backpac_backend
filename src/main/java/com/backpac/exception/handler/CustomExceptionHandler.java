package com.backpac.exception.handler;

import com.backpac.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * <pre>
 * com.backpac.exception.handler
 * ㄴ CustomExceptionHandler.java
 * </pre>
 * @date : 2021-03-31 오후 4:32
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 에러처리 공통 핸들러
 **/
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    /**
     * <pre>
     * handleException
     * Exception 발생시 에러처리
     * </pre>
     * @param request HttpServletRequest
     * @param ex Exception
     * @return org.springframework.http.ResponseEntity<?>
     * @author 김재범
     **/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception ex) {
        log.info("==================================== ex:"+ex);
        ErrorResponseDto response = ErrorResponseDto.of(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * <pre>
     * handleException
     * ConstraintViolationException (입력필드 validation 에러) 발생시 에러처리
     * </pre>
     * @param request HttpServletRequest
     * @param ex ConstraintViolationException
     * @return org.springframework.http.ResponseEntity<?>
     * @author 김재범
     **/
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(HttpServletRequest request, ConstraintViolationException ex) {
        log.info("==================================== ConstraintViolationException:"+ex);
        ErrorResponseDto response = ErrorResponseDto.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
        while(ex.getCause() != null) {
            Throwable nex = ex.getCause();
            response = ErrorResponseDto.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), nex.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
