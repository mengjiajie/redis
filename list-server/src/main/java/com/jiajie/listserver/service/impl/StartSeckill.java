package com.jiajie.listserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiajie.listserver.entity.Message;
import com.jiajie.listserver.service.Consumer;
import com.jiajie.listserver.service.Producer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;

import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class StartSeckill {

    private final Consumer consumer;
    private final Producer producer;
    @Scheduled(cron = "0/2 * * * * ?")
    //5秒执行一次
    @Transactional
    public void startSeckill() {
        //判断条件满足后方可添加
        Message message = new Message(1,"消息1");
        producer.produce(JSON.toJSONString(message));
        log.info("生产消息中……");
    }

}
