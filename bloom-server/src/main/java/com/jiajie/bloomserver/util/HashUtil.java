package com.jiajie.bloomserver.util;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class HashUtil {
    //预计数据总量
    private final static long SIZE = 1000000;
    //容错率
    private final static double FPP = 0.01;
    //二进制向量大小
    public static final long NUM_BITS;
    //哈希算法数量
    public static final int NUM_HASH_FUNCTIONS;
    static {
        NUM_BITS = optimalNumOfBits();
        NUM_HASH_FUNCTIONS = optimalNumOfHashFunctions();
    }

    //计算哈希值(算法借鉴)
    public static long hash(String key) {
        Charset charset = Charset.defaultCharset();
        return Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asLong();
    }

    //计算二进制向量大小(算法借鉴)
    private static long optimalNumOfBits(){
        return (long)((double)(-SIZE) * Math.log(FPP) / (Math.log(2.0D) * Math.log(2.0D)));
    }
    //计算哈希算法数量(算法借鉴)
    private static int optimalNumOfHashFunctions() {
        return Math.max(1, (int)Math.round((double)NUM_BITS / (double)SIZE * Math.log(2.0D)));
    }

}
