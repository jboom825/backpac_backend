package com.backpac.controller;

import com.backpac.annotation.RequiredAuthorization;
import com.backpac.dto.*;
import com.backpac.service.UsersService;
import com.backpac.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * com.backpac.controller
 * ㄴ UsersController.java
 * </pre>
 * @date : 2021-03-31 오전 10:10
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원 정보 Rest Controller
 **/
@Slf4j
@RequestMapping ("/api/")
@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    /**
     * <pre>
     * login
     * 회원 로그인
     *  - Swagger UI에서 테스트 하기 위해 등록. 실제로는 이쪽 메서드를 타지 않고 스프링 시큐어 필터를 호출함
     * </pre>
     * @param user LoginUserDto
     * @return java.lang.String
     * @author 김재범
     **/
    @PostMapping(value = "/login")
    @ApiOperation(value = "로그인", notes = "로그인 API")
    public String login(@RequestBody LoginUserDto user) {
        log.info("login!!");
        return "Authorization Bearer Token";
    }

    /**
     * <pre>
     * logout
     * 회원 로그아웃
     * </pre>
     * @param request HttpServletRequest
     * @return com.backpac.dto.CommonDto
     * @author 김재범
     **/
    @RequiredAuthorization
    @PostMapping(value = "/logout")
    @ApiOperation(value = "로그아웃", notes = "로그아웃 API")
    @ResponseBody
    public CommonDto logout(HttpServletRequest request) {
        log.info("logout!!");
        usersService.logout((String) request.getAttribute(JwtTokenUtil.JWT_TOKEN_KEY));
        return new CommonDto("0", "정상적으로 로그아웃 되었습니다.");
    }

    /**
     * <pre>
     * join
     * 회원 신규 등록
     * </pre>
     * @param user UserDto
     * @return com.backpac.dto.UserDto
     * @author 김재범
     **/
    @PostMapping(value = "/user/join")
    @ResponseBody
    @ApiOperation(value = "신규 회원가입", notes = "신규 회원가입 API")
    public UserDto join(@RequestBody UserDto user) {
        return usersService.insertUser(user);
    }

    /**
     * <pre>
     * getUserInfo
     * 단일 회원 상세 조회
     * </pre>
     * @param id String
     * @return com.backpac.dto.UserOrderDto
     * @author 김재범
     **/
    @RequiredAuthorization
    @GetMapping(value="/user/info/{userId}")
    @ApiOperation(value = "단일 회원 상세 정보조회", notes = "단일 회원 상세 정보조회 API")
    public UserOrderDto getUserInfo(@PathVariable("userId") String id) {
        return usersService.selectUser(id);
    }

    /**
     * <pre>
     * getUserInfoOrder
     * 단일 회원 주문 목록 조회
     * </pre>
     * @param id String
     * @return java.util.List<com.backpac.dto.OrderDto>
     * @author 김재범
     **/
    @RequiredAuthorization
    @GetMapping(value="/user/order/{userId}")
    @ApiOperation(value = "단일 회원 주문 정보조회", notes = "단일 회원 주문 정보조회 API")
    public List<OrderDto> getUserInfoOrder(@PathVariable("userId") String id) {
        return usersService.selectUserOrders(id);
    }

    /**
     * <pre>
     * getAllUserInfoOrder
     * 여러 회원 목록 조회
     *  - 페이징 처리
     *  - 이름, 이메일 검색기능
     *  - 각 회원별 마지막 주문정보
     * </pre>
     * @param page int
     * @param name String
     * @param email String
     * @return java.util.List<com.backpac.dto.UserOrderDto>
     * @author 김재범
     **/
    @RequiredAuthorization
    @GetMapping(value="/user/order")
    @ApiOperation(value = "모든 회원 상세 정보조회", notes = "모든 회원 상세 정보조회 API")
    public List<UserOrderDto> getAllUserInfoOrder(@RequestParam(value="page", defaultValue = "1") int page,
                                                  @RequestParam(value="name", required = false) String name,
                                                  @RequestParam(value="email", required = false) String email) {

        return usersService.selectAllUsers(page, name, email);
    }
}
