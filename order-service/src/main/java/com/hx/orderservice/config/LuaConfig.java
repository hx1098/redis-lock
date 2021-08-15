package com.hx.orderservice.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 16:35
 * @description
 * @editUser hx
 * @editTime 2021/8/15 16:35
 * @editDescription
 */
@Configuration
public class LuaConfig {

    @Bean(name = "set")
    public DefaultRedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/lock-set.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean(name = "del")
    public DefaultRedisScript<Boolean> redisScriptDel() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/lock.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }




}
