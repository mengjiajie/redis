package com.jiajie.redissonserver.api;

import com.jiajie.redissonserver.service.SpikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis")
@Slf4j
public class RedisController {

    @Autowired
    SpikeService spikeService;

    @RequestMapping(value = "do1", method = RequestMethod.POST)
    public Boolean do1() {
        return spikeService.spike();
    }









}
