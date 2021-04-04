package com.backpac.security;

import com.backpac.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.backpac.security
 * ㄴ CustomAuthenticationManager.java
 * </pre>
 * @date : 2021-04-02 오전 11:13
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 AuthenticationManager
 *         로그인한 사용자의 패스워드를 BCrypt 해쉬 함수를 사용하여 기존 BCrypt 해쉬 함수로 저장된 패스워드와 비교 검증한다.
 **/
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;


    /**
     * <pre>
     * authenticate
     * 로그인한 사용자의 authentication 정보로 적합한 사용자인지 검증
     * : 평문 패스워드를 BCrypt 해쉬 함수를 사용하여 비밀번호 비교 검증
     * </pre>
     * @param authentication Authentication
     * @return org.springframework.security.core.Authentication
     * @throws AuthenticationException 인증오류
     * @author 김재범
     **/
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String id = token.getName();
        String passWord = (String)token.getCredentials();
        UserDetails user = userDetailsServiceImpl.loadUserByUsername(id);
        if(!bCryptPasswordEncoder.matches(passWord, user.getPassword())) {
            throw new BadCredentialsException("invalid password.");
        }
        return new UsernamePasswordAuthenticationToken(user, passWord, user.getAuthorities());
    }
}
