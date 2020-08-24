package com.jiajie.bloomserver.controller;

import com.jiajie.bloomserver.service.RedisBloomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class RedisBloomController {

    @Autowired
    private RedisBloomService redisBloomService;

    @RequestMapping("/{id}")
    public void test(@PathVariable("id") String id) {
        redisBloomService.init();
        redisBloomService.bloom(id);
    }

}
