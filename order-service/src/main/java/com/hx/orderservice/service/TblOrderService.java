package com.hx.orderservice.service;

import com.hx.orderservice.entity.TblOrder;

import java.util.List;

/**
 * (TblOrder)表服务接口
 *
 * @author makejava
 * @since 2021-08-12 17:42:12
 */
public interface TblOrderService {


    /**
     * 判断四级的抢单是否成功, 以及抢单成功后的业务
     * @param orderId
     * @param driverId
     * @return
     */
    public boolean grab(int orderId, int driverId);

}
