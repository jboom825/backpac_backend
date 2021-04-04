package com.backpac.dto;

import com.backpac.entity.Orders;
import com.backpac.entity.OrdersPk;
import com.backpac.entity.Users;
import com.backpac.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * com.backpac.dto
 * ㄴ UserOrderDto.java
 * </pre>
 * @date : 2021-04-04 오전 09:40
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 회원 주문정보 DTO
 **/
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDto extends UserDto{

    private List<OrderDto> orders;

    public static UserOrderDto of(Users user) {
        UserOrderDto UserOrderDto = ModelMapperUtil.getMapper().map(user, UserOrderDto.class);
        List<OrderDto> ordersList = new ArrayList<>();
        for (Orders orders1 : user.getOrders()) {
            OrderDto orderDto = new OrderDto();
            if(orders1.getOrdersPK() == null) continue;
            OrdersPk ordersPK = orders1.getOrdersPK();
            orderDto.setUserId(ordersPK.getUserId());
            orderDto.setProductName(ordersPK.getProductName());
            orderDto.setPaymentDate(ordersPK.getPaymentDate().toString());
            ordersList.add(orderDto);
        }
        UserOrderDto.setOrders(ordersList);
        return UserOrderDto;
    }
}
