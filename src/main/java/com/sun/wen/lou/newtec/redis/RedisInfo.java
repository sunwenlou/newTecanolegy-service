package com.sun.wen.lou.newtec.redis;

import java.io.Serializable;

import redis.clients.jedis.Protocol;

/**
 * redis基本信息实体
 */
public class RedisInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//主机名称（监控集群配置的redis主机hostname）
	protected String master = "redis_master";
	
	//主机IP(监控集群配置的redis主机IP地址)
	protected String host = "127.0.0.1";
	
	//Redis监控集群默认监听端口
	protected int port = 26379;
	
	//客户端空闲n秒后断开连接；默认是 0 表示不断开。
	protected int timeout = 0;
	
	//指定一个密码，客户端连接时也需要通过密码才能成功连接
	protected String password = "";
	
	//redis数据库（0-15个）默认为0
	protected int database = Protocol.DEFAULT_DATABASE;
	
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

}
