package com.jiajie.redissonserver.service.impl;

import com.jiajie.redissonserver.service.DistributedLocker;
import com.jiajie.redissonserver.service.SpikeService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SpikeServiceImpl implements SpikeService {
    @Autowired
    DistributedLocker distributedLocker;
    @Autowired
    private ExecutorService consumerQueueThreadPool;

    private static Integer NUM = 10;

    @Override
    public boolean spike() {
        try {
            RLock lock = distributedLocker.lock(UUID.randomUUID().toString());
            boolean tryLock = lock.tryLock(30, 10, TimeUnit.SECONDS);
            if (tryLock) {
                if (NUM > 0) {
                    NUM--;
                    log.info("还剩下库存{}", NUM);
                    lock.unlock();
                }else {
                    log.info("秒杀失败,已经卖完");
                }
            }
            return true;
        } catch (InterruptedException e) {
            log.error("秒杀失败");
            return false;
        }
    }
}
