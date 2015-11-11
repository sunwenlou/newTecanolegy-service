package com.sun.wen.lou.newtec.redis;


public class HCRedisManager {
	
	private RedisClient redisClient;

	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
		redisClient.init();
	}
}
