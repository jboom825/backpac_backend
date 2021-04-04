package com.backpac.repository;

import com.backpac.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * com.backpac.repository
 * ㄴ OrdersRepository.java
 * </pre>
 * @date : 2021-03-31 오후 2:15
 * @author : 김재범
 * @version : 1.0.0
 * @desc : Orders Entity를 처리하기 위한 Repository
 **/
public interface OrdersRepository extends JpaRepository<Orders, String> {
}
