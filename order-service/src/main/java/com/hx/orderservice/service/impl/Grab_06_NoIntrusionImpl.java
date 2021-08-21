package com.hx.orderservice.service.impl;

import com.hx.orderservice.annotation.DistributedLock;
import com.hx.orderservice.constant.RedisKeyConstant;
import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 13:21
 * @description
 * @editUser hx
 * @editTime 2021/8/15 13:21
 * @editDescription RedLock的方式
 */
@Service("noIntrusionImpLockService")
@Slf4j
public class Grab_06_NoIntrusionImpl implements GrabService {


    @Autowired
    TblOrderService tblOrderService;


    @DistributedLock(value = "redisLockRegistry",time = 1)
    @Override
    public String grabOrder(int orderId, int driverId) {
        log.info(" 司机开始抢单: driverId = [{}] 执行抢单逻辑 ", driverId );

        boolean grab = tblOrderService.grab(orderId, driverId);
        if (grab) {
            System.out.println("司机" + driverId + "抢单成功!");
        } else {
            System.out.println("司机" + driverId + "抢单失败!");
        }
        return null;
    }
}
