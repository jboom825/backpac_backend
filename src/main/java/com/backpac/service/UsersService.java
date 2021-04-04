package com.backpac.service;

import com.backpac.dto.OrderDto;
import com.backpac.dto.UserDto;
import com.backpac.dto.UserOrderDto;

import java.util.List;

/**
 * <pre>
 * com.backpac.service
 * ㄴ UsersService.java
 * </pre>
 * @date : 2021-04-01 오후 1:14
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원(사용자) 서비스 인터페이스
 **/
public interface UsersService {
    void logout(String token);
    UserDto insertUser(UserDto user);
    UserOrderDto selectUser(String id);
    List<OrderDto> selectUserOrders(String id);
    List<UserOrderDto> selectAllUsers(int page, String name, String email);
}
