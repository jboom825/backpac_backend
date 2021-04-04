package com.backpac.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * com.backpac.annotation
 * ㄴ RequiredAuthorization.java
 * </pre>
 * @date : 2021-04-01 오후 1:53
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 로그인이 필요한 API를 표현하기 위한 커스텀 어노테이션
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequiredAuthorization {
    boolean value() default true;
}
