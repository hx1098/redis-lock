package com.hx.orderservice.entity;

import java.io.Serializable;

/**
 * (TblOrderLock)实体类
 *
 * @author makejava
 * @since 2021-08-12 17:42:15
 */
public class TblOrderLock implements Serializable {
    private static final long serialVersionUID = 928922237104192984L;

    private Integer orderId;

    private Integer driverId;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

}
