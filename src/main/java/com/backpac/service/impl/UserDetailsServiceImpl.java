package com.backpac.service.impl;

import com.backpac.exception.UserNotFoundException;
import com.backpac.repository.UsersRepository;
import com.backpac.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * <pre>
 * com.backpac.service.impl
 * ㄴ UserDetailsServiceImpl.java
 * </pre>
 * @date : 2021-04-01 오후 3:56
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 UserDetailsService 를 구현한 서비스
 *         회원 ID 정보로 CustomUserDetail 조회
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository repository;

    /**
     * <pre>
     * loadUserByUsername
     * 회원 ID 정보로 CustomUserDetail 조회
     * </pre>
     * @param id String
     * @return org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException 사용자없음에러
     * @author 김재범
     **/
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return repository.findById(id).map(u -> new CustomUserDetail(u)).orElseThrow(() -> new UserNotFoundException());
    }
}
