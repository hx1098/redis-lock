package com.hx.orderservice.service;

/**
 * @author hx
 * @createTime 2021/8/12 20:44
 * @version 1.0.0
 * @description
 * @editUser hx
 * @editTime 2021/8/12 20:44
 * @editDescription
 */
public interface GrabService {


    /**
     * 前提:
     * 1.必须是同一个乘客
     * 2.必须是同一个订单推送给了多个司机, 这里直接使用Jmeter软件, 来模拟多个司机并发抢单的过程 ( 这里不考虑推送的过程, 仅仅演示抢单的核心)
     *
     * 司机抢单的接口, 多种的实现逻辑
     * @param orderId
     * @param driverId
     */
     String grabOrder(int orderId, int driverId);


}
