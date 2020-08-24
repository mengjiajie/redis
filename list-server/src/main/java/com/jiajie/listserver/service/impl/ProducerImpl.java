package com.jiajie.listserver.service.impl;

import com.jiajie.listserver.service.Producer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProducerImpl implements Producer {
    private final static String CHANNL = "string-topic";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void produce(Object msg) {
        redisTemplate.convertAndSend(CHANNL, msg);
    }
}
