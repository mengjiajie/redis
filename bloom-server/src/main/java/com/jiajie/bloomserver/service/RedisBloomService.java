package com.jiajie.bloomserver.service;

public interface RedisBloomService {

    void init();

    void bloom(String id);

    void noFilter(String id);
}
