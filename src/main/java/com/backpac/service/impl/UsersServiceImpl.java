package com.backpac.service.impl;

import com.backpac.dto.OrderDto;
import com.backpac.dto.UserDto;
import com.backpac.entity.Orders;
import com.backpac.entity.Users;
import com.backpac.dto.UserOrderDto;
import com.backpac.exception.AlreadyExistUserException;
import com.backpac.exception.UserNotFoundException;
import com.backpac.repository.UsersRepository;
import com.backpac.service.UsersService;
import com.backpac.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <pre>
 * com.backpac.service.impl
 * ㄴ UsersServiceImpl.java
 * </pre>
 * @date : 2021-04-01 오후 1:15
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원(사용자) 서비스 구현체
 **/
@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    //페이지당 보여줄 갯수
    final int PAGE_PER_COUNT = 5;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UsersRepository repository;

    /**
     * <pre>
     * logout
     * 회원 로그아웃
     *  - Redis에 현재 토큰을 저장하여 만료된 토큰으로 판단한다. (AuthorizationInterceptor에서 검증)
     * </pre>
     * @param token String
     * @author 김재범
     **/
    @Override
    public void logout(String token) {
        ValueOperations<String, String>values = redisTemplate.opsForValue();
        values.set(token, token, jwtTokenUtil.getExpired(), TimeUnit.SECONDS);
    }

    /**
     * <pre>
     * insertUser
     * 회원 등록
     *  - 패스워드는 BCrypt 해시 처리를 하여 저장
     * </pre>
     * @param user UserDto
     * @return com.backpac.dto.UserDto
     * @author 김재범
     **/
    @Override
    public UserDto insertUser(UserDto user) {
        if(repository.findById(user.getId()).isPresent()) {
            throw new AlreadyExistUserException("이미 존재하는 사용자입니다.");
        }

        user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
        log.debug("USER:"+user);
        repository.save(Users.of(user));
        return user;
    }

    /**
     * <pre>
     * selectUser
     * 단일 회원 상세 조회
     * </pre>
     * @param id String
     * @return com.backpac.dto.UserOrderDto
     * @throws UserNotFoundException 사용자없음에러
     * @author 김재범
     **/
    @Override
    public UserDto selectUser(String id) {
        return UserDto.of(repository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    /**
     * <pre>
     * selectUserOrders
     * 단일 회원의 주문 목록 조회
     * </pre>
     * @param id String
     * @return java.util.List<com.backpac.dto.OrderDto>
     * @throws UserNotFoundException 사용자없음에러
     * @author 김재범
     **/
    @Override
    @Transactional
    public List<OrderDto> selectUserOrders(String id) {
        Users users = repository.findById(id).orElseThrow(UserNotFoundException::new);
        List<Orders> ordersList = users.getOrders();

        return ordersList.stream().map(OrderDto::of).collect(Collectors.toList());
    }

    /**
     * <pre>
     * selectAllUsers
     * 여러 회원 목록 조회
     *  - 페이지 처리
     *  - 이름, 이메일 검색조건으로 조회(페이징 연동)
     *  - 회원별 마지막 주문정보만 추가로 노출
     * </pre>
     * @param page int
     * @param name String
     * @param email String
     * @return java.util.List<com.backpac.dto.UserOrderDto>
     * @author 김재범
     **/
    @Override
    public List<UserOrderDto> selectAllUsers(int page, String name, String email) {
        List<Users> usersList;
        if(!ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(email)) {
            usersList = repository.findbyNameAndEmailFetchJoin(name, email,
                    PageRequest.of(page-1, PAGE_PER_COUNT, Sort.by(Sort.Direction.ASC, "id")));
        }else if(!ObjectUtils.isEmpty(name)) {
            usersList = repository.findbyNameFetchJoin(name,
                    PageRequest.of(page-1, PAGE_PER_COUNT, Sort.by(Sort.Direction.ASC, "id")));
        }else if(!ObjectUtils.isEmpty(email)) {
            usersList = repository.findbyEmailFetchJoin(email,
                    PageRequest.of(page-1, PAGE_PER_COUNT, Sort.by(Sort.Direction.ASC, "id")));
        }else{
            usersList = repository.findbyAllFetchJoin(PageRequest.of(page-1, PAGE_PER_COUNT, Sort.by(Sort.Direction.ASC, "id")));
        }

        for(Users u:usersList) {
            List<Orders> ordersList = new ArrayList<>();
            ordersList.add(u.getOrders().stream()
                    .sorted(Comparator.comparing(orders -> orders.getOrdersPK().getPaymentDate()))
                    .skip(u.getOrders().size()  == 0 ? 0: u.getOrders().size() - 1)
                    .findFirst().orElseGet(Orders::new)
                    );
            u.setOrders(ordersList);
        }
        return usersList.stream().map(UserOrderDto::of).collect(Collectors.toList());
    }


}
