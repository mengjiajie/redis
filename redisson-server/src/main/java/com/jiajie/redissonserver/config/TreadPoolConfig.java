package com.jiajie.redissonserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author liaohonglai
 * @date 2019/10/31
 */
@Configuration
public class TreadPoolConfig {

	/**
	 * 消费队列线程
	 * @return
	 */
	@Bean
	public ExecutorService buildConsumerQueueThreadPool(){
		ExecutorService pool = new ThreadPoolExecutor(10, 5000, 5L, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(20000),new ThreadPoolExecutor.AbortPolicy());
		return pool ;
	}

}
