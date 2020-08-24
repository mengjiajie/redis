package com.jiajie.bloomserver.service.impl;

import com.jiajie.bloomserver.service.RedisBloomService;
import com.jiajie.bloomserver.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class RedisBloomFilter implements RedisBloomService {

    //预计数据总量
    private long size = 1000000;
    //容错率
    private double fpp = 0.01;

    //哈希算法数量
    private int numHashFunctions;
    //redis中的key
    private final String key = "goods_filter";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void init() {
        put("1");
        put("2");
        put("3");
        put("4");
    }

    @Override
    public void bloom(String id) {

        //先查询布隆过滤器，过滤掉不可能存在的数据请求
        if (!isExist(id)) {
            System.err.println("id:" + id + ",布隆过滤...");
        } else {
            //布隆过滤器认为可能存在，再走流程查询
            noFilter(id);
        }
    }

    //不使用过滤器
    @Override
    public void noFilter(String id) {
        //先查Redis缓存
        Object o = redisTemplate.opsForValue().get(id);
        if (o != null) {
            //命中缓存
            System.err.println("id:" + id + ",命中redis缓存...");
        }
        //缓存未命中 查询数据库
        System.err.println("id:" + id + ",查询DB...");
        //结果存入Redis
        redisTemplate.opsForValue().set(id, "goods");
    }

    /**
     * 向布隆过滤器中put
     *
     * @param id
     */
    public void put(String id) {
        long[] indexs = getIndexs(id);
        //将对应下标改为1
        for (long index : indexs) {
            redisTemplate.opsForValue().setBit(key, index, true);
        }
    }

    /**
     * 判断id是否可能存在
     *
     * @param id
     * @return
     */
    public boolean isExist(String id) {
        long[] indexs = getIndexs(id);
        //只要有一个bit位为1就表示可能存在
        for (long index : indexs) {
            if (redisTemplate.opsForValue().getBit(key, index)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据key获取bitmap下标(算法借鉴)
     *
     * @param key
     * @return
     */
    public long[] getIndexs(String key) {
        long hash1 = HashUtil.hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[HashUtil.NUM_HASH_FUNCTIONS];
        for (int i = 0; i < HashUtil.NUM_HASH_FUNCTIONS; i++) {
            long combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % HashUtil.NUM_BITS;
        }
        return result;
    }
}
