package com.cqndt.sentinelserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/test")
    public void sentinel(){
        stringRedisTemplate.opsForValue().set("meng","jiajie");
    }

}
