package com.backpac.security;

import com.backpac.entity.Users;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * <pre>
 * com.backpac.security
 * ㄴ CustomUserDetail.java
 * </pre>
 * @date : 2021-04-01 오후 3:42
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 UserDetails를 구현한 DTO 클래스
 *         Users entity에 값들을 위임하여 AuthenticationManager에서 필드값을 사용할수 있도록함
 **/
public class CustomUserDetail implements UserDetails {

    @Delegate
    private final Users user;

    public CustomUserDetail(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
