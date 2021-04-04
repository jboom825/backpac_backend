package com.backpac.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * <pre>
 * com.backpac.security
 * ㄴ WebSecurityConfig.java
 * </pre>
 * @date : 2021-04-01 오전 10:15
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 시큐리티 WebSecurityConfigurerAdapter 를 상속받아 web 요청 제외항목(시큐리티) 설정과
 *         http 보안 및 세션 설정(토큰으로 로그인하기 위해)
 **/
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    /**
     * <pre>
     * configure
     * 스프링 시큐리티 요청 제외 항목 설정
     * </pre>
     * @param web WebSecurity
     * @author 김재범
     **/
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/swagger-ui.html")
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/favicon/**")
                .antMatchers("/img/**")
                .antMatchers("/images/**");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * <pre>
     * configure
     * http 보안 및 세션 설정
     * - csrf 설정 / 폼로그인 블가 / 세션방식 로그인 사용안함
     * </pre>
     * @param http HttpSecurity
     * @author 김재범
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .formLogin().disable();

    }

    /**
     * <pre>
     * customLoginTokenFilter
     * CustomLoginTokenFilter Bean 등록
     * - 필터대상 URL 과 인증 성공,실패시 처리 핸들러 등록
     * </pre>
     * @return com.backpac.security.CustomLoginTokenFilter
     * @author 김재범
     **/
    @Bean
    public CustomLoginTokenFilter customLoginTokenFilter() {
        CustomLoginTokenFilter filter = new CustomLoginTokenFilter(customAuthenticationManager);
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(customAuthenticationFailHandler());
        return filter;
    }
    
    /**
     * <pre>
     * customAuthenticationSuccessHandler
     * CustomAuthenticationSuccessHandler Bean 등록
     * </pre>
     * @return com.backpac.security.CustomAuthenticationSuccessHandler
     * @author 김재범
     **/
    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * <pre>
     * customAuthenticationFailHandler
     * CustomAuthenticationFailHandler Bean 등록
     * </pre>
     * @return com.backpac.security.CustomAuthenticationFailHandler
     * @author 김재범
     **/
    @Bean
    public CustomAuthenticationFailHandler customAuthenticationFailHandler() {
        return new CustomAuthenticationFailHandler();
    }
}
