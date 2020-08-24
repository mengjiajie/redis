package com.jiajie.listserver.listener;

import com.alibaba.fastjson.JSON;
import com.jiajie.listserver.service.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import sun.dc.pr.PRError;
import sun.rmi.runtime.Log;

import java.util.concurrent.locks.Lock;

@Slf4j
public class ConsumerRedisListener implements MessageListener {
    @Autowired
    private Consumer consumer;
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        Lock lock = redisLockRegistry.obtain("lock");
        log.info("监听到消息：{}", message);
        try {
            //加锁
            lock.lock();
            consumer.comsumer(message);
        } catch (Exception e) {
            log.error("消费异常：{}", e.getMessage());
        } finally {
            //解锁
            lock.unlock();
        }
    }
}
