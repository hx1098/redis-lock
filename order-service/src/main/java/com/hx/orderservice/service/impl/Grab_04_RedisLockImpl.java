package com.hx.orderservice.service.impl;

import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.client.codec.Codec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/14 21:29
 * @description
 * @editUser hx
 * @editTime 2021/8/14 21:29
 * @editDescription redis锁
 */
@Service("grabRedisLockService")
public class Grab_04_RedisLockImpl implements GrabService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TblOrderService orderService;


    @Override
    public String grabOrder(int orderId, int driverId) {

        String lock = "order_" + (orderId + "");



        /*
         *  情况一，如果锁没执行到释放，比如业务逻辑执行一半，运维重启服务，或 服务器挂了，没走 finally，怎么办？
         *  加超时时间
         *  setnx
         */
//    	boolean lockStatus = stringRedisTemplate.opsForValue().setIfAbsent(lock.intern(), driverId+"");
//    	if(!lockStatus) {
//    		return null;
//    	}

        /*
         *  情况二：加超时时间,会有加不上的情况，运维重启
         */
//    	boolean lockStatus = stringRedisTemplate.opsForValue().setIfAbsent(lock.intern(), driverId+"");
//    	stringRedisTemplate.expire(lock.intern(), 30L, TimeUnit.SECONDS);
//    	if(!lockStatus) {
//    		return null;
//    	}

        /**
         * 生产环境汇中, 会有超时时间加不上的情况,所有超时的时间不应该分为两行代码来执行,应该合并成一行
         *
         */
        Boolean lockstatus = stringRedisTemplate.opsForValue().setIfAbsent(lock.intern(), driverId + "", 30L, TimeUnit.SECONDS);
        if (!lockstatus) {
            return null;
        }

        try {
            System.out.println("司机:"+driverId+" 执行抢单逻辑");

            boolean b = orderService.grab(orderId, driverId);
            if(b) {
                System.out.println("司机:"+driverId+" 抢单成功");
            }else {
                System.out.println("司机:"+driverId+" 抢单失败");
            }

        } finally {
            /**
             * 这种释放锁有，可能释放了别人的锁。
             */
        	//stringRedisTemplate.delete(lock.intern());

            /**
             *  产生的原因: 不知道自己的代码要执行多长时间  或者 自己的锁过期了, 别人抢到了这把锁, 这把锁也是orderID, 在执行的过程中, 突然你释放了, 就直接叽叽了.
             * 下面代码避免释放别人的锁, 一定要判断释放的锁是否是自己的锁.
             */
            if((driverId+"").equals(stringRedisTemplate.opsForValue().get(lock.intern()))) {
                stringRedisTemplate.delete(lock.intern());
            }
        }

        return null;
    }
}
