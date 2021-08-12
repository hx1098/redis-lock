package com.hx.orderservice.entity;

import java.io.Serializable;

/**
 * (TblOrder)实体类
 *
 * @author makejava
 * @since 2021-08-12 17:42:10
 */
public class TblOrder implements Serializable {
    private static final long serialVersionUID = 183101540816437519L;

    private Integer orderId;

    private Integer orderStatus;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

}
