package com.hx.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/21 9:42
 * @description
 * @editUser hx
 * @editTime 2021/8/21 9:42
 * @editDescription
 */
@Configuration
public class RedisLockConfig {


    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return  new RedisLockRegistry(redisConnectionFactory,"order_lock");
    }

}
