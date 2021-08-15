package com.hx.orderservice.service.impl;

import com.hx.orderservice.constant.RedisKeyConstant;
import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import com.netflix.discovery.converters.Auto;
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
@Service("grabRedisRedissonRedLockLockService")
public class Grab_05_RedLockImpl implements GrabService {

    @Autowired
    RedissonClient redissonRed1;

    @Autowired
    RedissonClient redissonRed2;

    @Autowired
    RedissonClient redissonRed3;

    @Autowired
    TblOrderService tblOrderService;

    @Override
    public String grabOrder(int orderId, int driverId) {
        String lockKey = (RedisKeyConstant.GRAB_LOCK_ORDER_KEY_PRE + orderId).intern();

        //红锁 redisson
        RLock   rLock1 = redissonRed1.getLock(lockKey);
        RLock   rLock2 = redissonRed2.getLock(lockKey);
        RLock   rLock3 = redissonRed3.getLock(lockKey);
        RedissonRedLock rLock = new RedissonRedLock(rLock1, rLock2, rLock3);

        try {
            rLock.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此代码默认 设置key 超时时间30秒，过10秒，再延时
            System.out.println("司机:"+driverId+" 执行抢单逻辑");

            boolean b = tblOrderService.grab(orderId, driverId);
            if(b) {
                System.out.println("司机:"+driverId+" 抢单成功");
            }else {
                System.out.println("司机:"+driverId+" 抢单失败");
            }

        } finally {
            rLock.unlock();
        }

        return null;
    }
}
