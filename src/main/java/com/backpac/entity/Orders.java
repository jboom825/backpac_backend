package com.backpac.entity;

import com.backpac.dto.OrderDto;
import com.backpac.util.ModelMapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * <pre>
 * com.backpac.entity
 * ㄴ Orders.java
 * </pre>
 * @date : 2021-03-31 오전 11:35
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 주문정보 Entity
 **/
@Getter @Setter
@Entity
@Table(name="ORDERS")
public class Orders {

    public Orders() {
    }

    @Builder
    public Orders(OrdersPk ordersPK) {
        this.ordersPK = ordersPK;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name="userId", column=@Column(name="USER_ID")),
            @AttributeOverride(name="productName", column=@Column(name="PRODUCT_NAME")),
            @AttributeOverride(name="paymentDate", column=@Column(name="PAYMENT_DATE"))
    })
    protected  OrdersPk ordersPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Users user;

    public static Orders of(OrderDto orderDto) {
        return ModelMapperUtil.getMapper().map(orderDto, Orders.class);
    }
}
