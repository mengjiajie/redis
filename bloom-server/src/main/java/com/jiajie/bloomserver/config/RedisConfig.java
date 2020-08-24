package com.jiajie.bloomserver.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configurable
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        StringRedisSerializer redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;

        redisTemplate.setKeySerializer(redisSerializer);

        redisTemplate.setHashKeySerializer(redisSerializer);

        //JdkSerializationRedisSerializer序列化方式;

        JdkSerializationRedisSerializer jdkRedisSerializer = new JdkSerializationRedisSerializer();

        redisTemplate.setValueSerializer(jdkRedisSerializer);

        redisTemplate.setHashValueSerializer(jdkRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
