package com.hx.orderservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 8:11
 * @description
 * @editUser hx
 * @editTime 2021/8/15 8:11
 * @editDescription
 */
@Component
public class RedisConfig {
    @Autowired
    RedisSentinelProperties properties;
    //以下为redisson锁，哨兵
//    @Bean(name = "redisson")
//    @Order(1)
//    public Redisson getRedisson(){
//
//        Config config = new Config();
//        config.useSentinelServers()
//                .setMasterName(properties.getMasterName())
//                .addSentinelAddress(properties.getAddress())
//                .setDatabase(0);
//        return (Redisson) Redisson.create(config);
//    }
    //以上为redisson 哨兵锁

    //region以下为红锁
    @Bean(name = "redissonRed1")
    @Primary
    public RedissonClient redissonRed1(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(0);
        return Redisson.create(config);
    }
    @Bean(name = "redissonRed2")
    public RedissonClient redissonRed2(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6380").setDatabase(0);
        return Redisson.create(config);
    }
    @Bean(name = "redissonRed3")
    public RedissonClient redissonRed3(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6381").setDatabase(0);
        return Redisson.create(config);
    }
    //endregion以上为红锁


    // 单个redis
//    @Bean
//    @ConditionalOnMissingBean(StringRedisTemplate.class)
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//    	StringRedisTemplate redisTemplate = new StringRedisTemplate();
//    	redisTemplate.setConnectionFactory(redisConnectionFactory);
//    	return redisTemplate;
//
//    }

    /**
     * 单个redisson
     *
     * @return
     */
    /*@Bean
    public RedissonClient redissonClient() {
    	Config config = new Config();
    	config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(0);

    	return Redisson.create(config);
    }*/
   /* @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379")
                .setDatabase(0)
        ;
        return (Redisson) Redisson.create(config);
    }*/
}