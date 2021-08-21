package com.hx.orderservice.aop;

import com.hx.orderservice.annotation.DistributedLock;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.stream.FactoryConfigurationError;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 17:09
 * @description
 * @editUser hx
 * @editTime 2021/8/15 17:09
 * @editDescription
 */
@Component
@Aspect
@Slf4j
public class LockAop {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private WebApplicationContext webApplicationContext;

    public LockAop(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }


    @Pointcut("@annotation(com.hx.orderservice.annotation.DistributedLock)")
    private void apiAop(){

    }

    /**
     * 定义要切的东西
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "apiAop()")
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Object[] args = point.getArgs();
        log.info("aroundApi::args = [{}]",args[0]);
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        /*当于一个锁的管理器，所有的分布式锁都可以从中获取*/
        RedisLockRegistry redisLockRegistry = (RedisLockRegistry)webApplicationContext.getBean(distributedLock.value());

        Lock lock = redisLockRegistry.obtain(signature.getName()+"_"+args[0]);
        boolean b = false;
        for (int i = 0; i < 3; i++) {
            b = lock.tryLock(distributedLock.time(), TimeUnit.SECONDS);
            log.info("线程[{}] 是否获取到了锁,{}",Thread.currentThread().getName(),b);
            if (b) {
                break;
            } else {
                continue;
            }
        }
        log.info("获取锁...");
        Object process = null;
        try {
            process = point.proceed();
        } catch (Exception e) {
            throw e;
        }finally {
            //如果这个锁还存在, 就去解锁, 没有就不用解锁了, 因为已经释放掉了
            Boolean orderLock = stringRedisTemplate.hasKey("order_lock");
            try {
                if (true) {
                    lock.unlock();
                }
            } catch (Exception e) {
                log.error("线程[{}] 没有抢到锁, 没锁了.... + ",Thread.currentThread().getName());
                //e.printStackTrace();
            }
        }
        return process;
    }

}
