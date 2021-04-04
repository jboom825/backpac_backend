package com.backpac.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * com.backpac.entity
 * ㄴ OrdersPk.java
 * </pre>
 * @date : 2021-03-31 오전 11:35
 * @author : 김재범
 * @version : 1.0.0
 * @desc : 주문정보 PK EmbeddedId Entity
 **/
@Getter
@Setter
@Embeddable
public class OrdersPk implements Serializable{

    public OrdersPk() {
    }

    @Builder
    public OrdersPk(String userId, String productName, LocalDateTime paymentDate) {
        this.userId = userId;
        this.productName = productName;
        this.paymentDate = paymentDate;
    }

    @Column
    private String userId;

    @Column
    private String productName;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss.SSS")
    private LocalDateTime paymentDate;
}
