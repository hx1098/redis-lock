package com.hx.orderservice.service.impl;

import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 7:58
 * @description
 * @editUser hx
 * @editTime 2021/8/15 7:58
 * @editDescription
 */
@Service("grabRedisRedissonService")
public class Grab_04_RedisRedissonServiceImpl implements GrabService {


   /* @Autowired
    Redisson redisson;*/

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    TblOrderService orderService;

    @Override
    public String grabOrder(int orderId, int driverId) {
        //生成key
        String lock = "order_"+(orderId+"");

        RLock rlock = redissonClient.getLock(lock.intern());

        /*redisson这个不要用了， 应该是版本有问题，调了老久的时间*/
       /* RLock lock1 = redisson.getLock(lock.intern());*/

        try {
            // 此代码默认 设置key 超时时间30秒，过10秒，再延时
            rlock.lock();

            //lock1.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            lock1.lock();
            System.out.println("司机:"+driverId+" 执行抢单逻辑");

            boolean b = orderService.grab(orderId, driverId);
            if(b) {
                System.out.println("司机:"+driverId+" 抢单成功");
            }else {
                System.out.println("司机:"+driverId+" 抢单失败");
            }

        } finally {
            rlock.unlock();
            //lock1.unlock();
        }
        return null;

    }
}
