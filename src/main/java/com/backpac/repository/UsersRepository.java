package com.backpac.repository;

import com.backpac.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <pre>
 * com.backpac.repository
 * ㄴ UsersRepository.java
 * </pre>
 * @date : 2021-03-31 오후 2:11
 * @author : 김재범
 * @version : 1.0.0
 * @desc : Users Entity를 처리하기 위한 Repository
 *         N+1 조회를 막고자 'join fetch' QUERY 설정
 **/
public interface UsersRepository extends JpaRepository<Users, String> {
    @Query("select distinct u from Users u left join fetch u.orders")
    List<Users> findbyAllFetchJoin(Pageable pageable);

    @Query("select distinct u from Users u left join fetch u.orders where u.name=:name")
    List<Users> findbyNameFetchJoin(@Param("name") String name, Pageable pageable);

    @Query("select distinct u from Users u left join fetch u.orders where u.email=:email")
    List<Users> findbyEmailFetchJoin(@Param("email") String email, Pageable pageable);

    @Query("select distinct u from Users u left join fetch u.orders where u.name=:name and u.email=:email")
    List<Users> findbyNameAndEmailFetchJoin(@Param("name") String name,@Param("email") String email, Pageable pageable);
}
