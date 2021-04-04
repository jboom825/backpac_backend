package com.backpac.repository;

import com.backpac.entity.Orders;
import com.backpac.entity.OrdersPk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class OrdersRepositoryTest {

    @Autowired
    OrdersRepository ordersRepository;

    @Test
    void save() {
        int userCnt = 100;
        int orderCnt = 10;
        for(int i=0; i< userCnt; i++) {
            String userNum = String.format("%03d", i);
            for(int j=0;j<orderCnt;j++) {
                String prdNum = String.format("%03d", j);
                OrdersPk orderId = new OrdersPk("ID" + userNum, "PRD" + prdNum, (LocalDateTime.now()).plusMinutes(Long.parseLong(prdNum)));
                Orders order = new Orders(orderId);
                ordersRepository.save(order);
            }
        }
    }
}