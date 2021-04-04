package com.backpac.config;

import com.backpac.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * com.backpac.config
 * ㄴ WebMvcConfig.java
 * </pre>
 * @date : 2021-03-31 오전 11:01
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 스프링 WEBMVC 설정
 **/
@Configuration
@EnableSwagger2
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * <pre>
     * addInterceptors
     * 토큰검증을 위한 authorizationInterceptor 추가
     * </pre>
     * @param registry InterceptorRegistry
     * @return void
     * @author 김재범
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(authorizationInterceptor());
    }

    /**
     * <pre>
     * authorizationInterceptor
     * AuthorizationInterceptor Bean 등록
     * </pre>
     * @return com.backpac.interceptor.AuthorizationInterceptor
     * @author 김재범
     **/
    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    /**
     * <pre>
     * bCryptPasswordEncoder
     * BCryptPasswordEncoder Bean 등록
     * </pre>
     * @return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     * @author 김재범
     **/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <pre>
     * addResourceHandlers
     * Swagger UI를 노출하기 위해 리소스 추가
     * </pre>
     * @param registry ResourceHandlerRegistry
     * @author 김재범
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * <pre>
     * api
     * Swagger UI 기본설정
     * </pre>
     * @return springfox.documentation.spring.web.plugins.Docket
     * @author 김재범
     **/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(getApiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.backpac.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    /**
     * <pre>
     * getConsumeContentTypes
     *  - Swagger ConsumeContentTypes 설정
     * </pre>
     * @return Set<String>
     * @author 김재범
     **/
    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    /**
     * <pre>
     * getProduceContentTypes
     *  - Swagger ProduceContentTypes 설정
     * </pre>
     * @return Set<String>
     * @author 김재범
     **/
    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    /**
     * <pre>
     * getApiInfo
     *  - Swagger API 정보 설정
     * </pre>
     * @return ApiInfo
     * @author 김재범
     **/
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("BACKPAC 과제 API")
                .description("[BACKPAC] 과제 API")
                .contact(new Contact("JBK Swagger", "", "jboom825@gmail.com"))
                .version("0.1")
                .build();
    }

    /**
     * <pre>
     * apiKey
     *  - Swagger UI에서 입력한 JWT 토큰을 전송할 헤더 키 매핑
     * </pre>
     * @return ApiKey
     * @author 김재범
     **/
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * <pre>
     * securityContext
     *  - Swagger UI에서 인증키(JWT)를 사용하기 위한 설정
     * </pre>
     * @return SecurityContext
     * @author 김재범
     **/
    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    /**
     * <pre>
     * defaultAuth
     *  - Swagger UI에서 인증키의 적용 범위 및 인증키 이름을 설정
     * </pre>
     * @return List<SecurityReference>
     * @author 김재범
     **/
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
}



