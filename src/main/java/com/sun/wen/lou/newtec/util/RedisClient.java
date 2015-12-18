package com.sun.wen.lou.newtec.util;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.HostAndPort;

public class RedisClient {

	static Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
	private static JedisCluster jc;

	public static JedisCluster getJc() {
		if (jc == null) {
			jedisClusterNodes.add(new HostAndPort("192.168.10.70", 7001));/*
			jedisClusterNodes.add(new HostAndPort("192.168.10.70", 7001));
			jedisClusterNodes.add(new HostAndPort("192.168.10.70", 7002));
			jedisClusterNodes.add(new HostAndPort("192.168.10.70", 7003));
			jedisClusterNodes.add(new HostAndPort("192.168.10.70", 7004));
			jedisClusterNodes.add(new HostAndPort("192.168.10.70", 7005));*/
			jc = new JedisCluster(jedisClusterNodes);
		}
		return jc;
	}

	public RedisClient() {

	}

}
