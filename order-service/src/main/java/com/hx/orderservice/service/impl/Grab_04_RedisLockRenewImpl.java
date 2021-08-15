package com.hx.orderservice.service.impl;

import com.hx.orderservice.service.RenewGrabLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author hx
 * @createTime 2021/8/15 7:27
 * @version 1.0.0
 * @description redis自动续期的功能, 生产环境中一般不用自己写,
 * @editUser hx
 * @editTime 2021/8/15 7:27
 * @editDescription
 */
@Service
public class Grab_04_RedisLockRenewImpl implements RenewGrabLockService {

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    @Override
    @Async
    public void renewLock(String key, String value, int time) {
        String v = redisTemplate.opsForValue().get(key);
        if (v.equals(value)){
            int sleepTime = time / 3;
            try {
                Thread.sleep(sleepTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisTemplate.expire(key,time, TimeUnit.SECONDS);
            renewLock(key,value,time);
        }

    }
}
