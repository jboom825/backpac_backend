package com.backpac.dto;

import com.backpac.entity.Orders;
import com.backpac.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * com.backpac.dto
 * ㄴ OrderDto.java
 * </pre>
 * @date : 2021-03-31 오후 2:55
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 주문정보 DTO
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String userId;
    private String productName;
    private String paymentDate;

    public static OrderDto of(Orders order) {
        return ModelMapperUtil.getMapper().map(order.getOrdersPK(), OrderDto.class);
    }
}
