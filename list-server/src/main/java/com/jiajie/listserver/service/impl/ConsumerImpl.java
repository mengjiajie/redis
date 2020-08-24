package com.jiajie.listserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiajie.listserver.entity.Message;
import com.jiajie.listserver.service.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ConsumerImpl implements Consumer {

    @Override
    public void comsumer(Object msg) {
        String str = String.valueOf(msg).substring(1, String.valueOf(msg).length() - 1).replace("\\", "");
        Message message = JSON.parseObject(String.valueOf(str), Message.class);
        log.info("开始消费消息: {}", message);
    }
}
