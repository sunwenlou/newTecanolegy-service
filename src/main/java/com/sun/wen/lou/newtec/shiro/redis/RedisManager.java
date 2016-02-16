package com.sun.wen.lou.newtec.shiro.redis;

import java.util.HashSet;
import java.util.Set;

import com.sun.wen.lou.newtec.redis.SerializeUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

/**
 * 之前是直接访问redis集群，为了解决单点故障问题，修改源码使它访问监控。
 * <P>Author : zhangjun </P>
 * <P>Date : 2015-1-23 </P>
 */
public class RedisManager {
	
	private String master = "redis-master";
	
	private String host = "127.0.0.1";
	
	private int port = 26379;
	
	// 0 - never expire
	private int expire = 0;
	
	//timeout for jedis try to connect to redis server, not expire time! In milliseconds
	private int timeout = 0;
	
	private String password = "";
	
	private int database = Protocol.DEFAULT_DATABASE;
	
	private static JedisSentinelPool jedisPool = null;
	
	public RedisManager(){
		
	}
	
//	public static void main(String[] args) {
//		RedisManager a = new RedisManager();
//		a.setMaster("redis-1");
//		a.setHost("192.168.100.24");
//		a.setPort(26379);
//		a.setDatabase(2);
//		a.init();
//	}
	
	/**
	 * 初始化方法
	 */
	public void init(){
		if(jedisPool == null){
			Set<String> sentinels = new HashSet<String>();
		    sentinels.add(new HostAndPort(getHost(),getPort()).toString());
		    //add by zhangjun 20150210
			if(password != null && !"".equals(password)){
				jedisPool = new JedisSentinelPool(getMaster(), sentinels,new JedisPoolConfig(),timeout,password,database);
			}else{
				jedisPool = new JedisSentinelPool(getMaster(), sentinels,new JedisPoolConfig(),timeout,null,database);
			}
		}
	}
	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.get(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * <P>Author : zhangjun </P>.
	 * <P>Date : 2015-1-23 </P>
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public String set(String key,Object object) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					jedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * <P>Author : zhangjun </P>.
	 * <P>Date : 2015-1-23 </P>
	 * @param key
	 * @param value
	 */
	public Object getObject(String key) {
		Object o = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bs = jedis.get(SerializeUtils.serialize(key));
			if(bs != null){
				o = SerializeUtils.unserialize(bs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					jedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return o;
	}
	
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * <P>Author : zhangjun </P>.
	 * <P>Date : 2015-1-23 </P>
	 * @param key
	 * @param value
	 */
	public String get(String key) {
		String str = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			str = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(jedis != null){
				try {
					jedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * <P>Author : zhangjun </P>.
	 * <P>Date : 2015-1-23 </P>
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public String set(final String key, final String value) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.set(key, value);
			if(this.expire != 0){
				jedis.expire(key, this.expire);
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				try {
					jedisPool.returnResource(jedis);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return l;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(this.expire != 0){
				jedis.expire(key, this.expire);
		 	}
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(expire != 0){
				jedis.expire(key, expire);
		 	}
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public Long del(byte[] key){
		Long flag;
		Jedis jedis = jedisPool.getResource();
		try{
			flag = jedis.del(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return flag;
	}
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public Long del(String key){
		Long flag;
		Jedis jedis = jedisPool.getResource();
		try{
			flag = jedis.del(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return flag;
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.flushDB();
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	
	
	/**
	 * size
	 */
	public Long dbSize(){
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try{
			dbSize = jedis.dbSize();
		}finally{
			jedisPool.returnResource(jedis);
		}
		return dbSize;
	}

	/**
	 * keys
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try{
			keys = jedis.keys(pattern.getBytes());
		}finally{
			jedisPool.returnResource(jedis);
		}
		return keys;
	}
	
	
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

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
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
