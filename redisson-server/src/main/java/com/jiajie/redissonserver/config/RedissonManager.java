package com.jiajie.redissonserver.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonManager {

    @Value("${redisson.address}")
    private  String cluster;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedisson(){
        String[] nodes = cluster.split(",");
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for(int i=0;i<nodes.length;i++){
            nodes[i] = "redis://"+nodes[i];
        }
        Config config = new Config();
//        config.useClusterServers() //这是用的集群server
//                .setScanInterval(2000) //设置集群状态扫描时间
//                .addNodeAddress(nodes).setRetryAttempts(5).setTimeout(10000);
        config.useSingleServer().setAddress(cluster);
        return Redisson.create(config);
    }

}
