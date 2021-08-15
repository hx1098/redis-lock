package com.hx.orderservice.controller;

import io.lettuce.core.output.BooleanOutput;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 16:41
 * @description
 * @editUser hx
 * @editTime 2021/8/15 16:41
 * @editDescription
 */
@RestController
@RequestMapping
public class LuaController {


    @Resource(name = "set")
    private DefaultRedisScript<Boolean> redisScript;

    @Resource(name = "del")
    private DefaultRedisScript<Boolean> redisScriptDel;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * lua脚本设置
     * @return
     */
    @GetMapping("/lua")
    public ResponseEntity luaGet(){
        List<String> keys = Arrays.asList("testLua","hello World");
        Boolean execute = stringRedisTemplate.execute(redisScript, keys, "100");
        assert execute != null;
        return ResponseEntity.ok(execute);
    }

    /**
     * lua删除
     * @return
     */
    @GetMapping("/lua-del")
    public ResponseEntity luaDel(){
        List<String> keys = Arrays.asList("testLua");
        Boolean execute = stringRedisTemplate.execute(redisScriptDel, keys, "hello World");
        assert execute != null;
        return ResponseEntity.ok(execute);
    }






}
