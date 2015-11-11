package com.sun.wen.lou.newtec.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;


/**
 * Spring专用 redis客户端
 */
public class RedisClient extends RedisInfo{
	
	private static final long serialVersionUID = 1L;
	
	//redis监控连接池
	private static JedisSentinelPool jedisPool = null;
	
	//初始化redis连接池(服务器启动Spring会初始化连接池)
	public void init(){
		if(jedisPool == null){
			Set<String> sentinels = new HashSet<String>();
		    sentinels.add(new HostAndPort(host,port).toString());
			jedisPool = new JedisSentinelPool(master, sentinels,new JedisPoolConfig(),timeout,null,database);
		}
	}
	
	/*************************对Key及String的操作开始****************************/
	
	/**
	 * 将字符串值 value 关联到 key 。
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
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.del(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
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
	
	
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public static String set(final String key, final String value) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.set(key, value);
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
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public static String set(String key,String value,int expire) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.set(key, value);
			if(expire != 0){
				jedis.expire(key,expire);
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
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public static String set(byte[] key, byte[] value) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.set(key, value);
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
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public static String set(String key,Object object) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
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
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public static String set(String key,Object object, int expire) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), SerializeUtils.serialize(object));
			if(expire != 0){
				jedis.expire(key.getBytes(), expire);
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
		return l;
	}
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。
	 */
	public static String setObj(String key,Object object, int expire) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
			if(expire != 0){
				jedis.expire(SerializeUtils.serialize(key), expire);
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
		return l;
	}
	
	
	/**
	 * 获取指定Key的Value，如果该Key不存在，返回null。
	 * @param key
	 * @return
	 */
	public static String get(final String key) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.get(key);
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
	 * 获取指定Key的Value，如果该Key不存在，返回null。
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		byte[] l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.get(key);
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
	 * 如果该Key已经存在，append命令将参数Value的数据追加到已存在Value的末尾。
	 * 如果该Key不存在，append命令将会创建一个新的Key/Value。返回追加后的Value的长度。
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long append(final String key, final String value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.append(key, value);
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
	 * 删除指定的Key
	 * @param key
	 * @return
	 */
	public static Long del(final String key) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.del(key);
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
	 * 判断该键是否存在，存在返回1，否则返回0。
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean exists(final String key) {
		boolean l = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.exists(key);
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
	 * 根据key取出value值得长度
	 * @return
	 */
	public static Long strlen(final String key) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.strlen(key);
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
	 * 设置某个key的过期时间（单位：秒）, 在超过该时间后，Key被自动的删除。
	 * 如果该Key在超时之前被修改，与该键关联的超时将被移除。
	 * @param key
	 * @param seconds
	 * @return
	 * 1表示成功
	 * 0 表示 key已经设置过过期时间或者不存在
	 */
	public static Long expire(String key, int seconds) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.expire(key, seconds);
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
	 * 设置某个key的过期时间（单位：秒）, 在超过该时间后，Key被自动的删除。
	 * 如果该Key在超时之前被修改，与该键关联的超时将被移除。
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static Long expire(byte[] key, int seconds) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.expire(key, seconds);
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
	 * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。
	 * 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static Long expireAt(String key, long unixTime) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.expireAt(key, unixTime);
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
	 * 将当前数据库中的mysetkey键移入到ID为dbIndex的数据库中
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public static Long move(String key, int dbIndex) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.move(key, dbIndex);
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
	 * 将当前数据库中的mysetkey键移入到ID为dbIndex的数据库中
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public static Long move(byte[] key, int dbIndex) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.move(key, dbIndex);
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
	 * 通过ttl命令查看一下指定Key的剩余存活时间(秒数)，0表示已经过期，-1表示永不过期。
	 * @param key
	 * @return
	 */
	public static long ttl(String key) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.ttl(key);
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
	 * 原子性的设置该Key为指定的Value，返回该Key的原有值，如果该Key之前并不存在，则返回null。
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getSet(String key, String value) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.getSet(key, value);
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
	 * 返回一组指定Keys的Values的列表。
	 * @param keys
	 * @return
	 */
	public static List<String> mget(String[] keys) {
		List<String> values = new ArrayList<String>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for(String key : keys){
				values.add(jedis.get(key));
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
		return values;
	}

	/**
	 * 如果指定的Key不存在，则设定该Key持有指定字符串Value，此时其效果等价于SET命令。
	 * 相反，如果该Key已经存在，该命令将不做任何操作并返回。
	 * @param key
	 * @param value
	 * @return 
	 * 设置成功，返回 1 。
	 * 设置失败，返回 0 。
	 */
	public static Long setnx(String key, String value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.setnx(key, value);
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
	 * 将字符串值 value 关联到 key ， 可以设置过期时间
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 * 设置成功时返回 OK 。
	 * 当 seconds 参数不合法时，返回一个错误
	 */
	public static String setex(String key, int seconds, String value) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.setex(key, seconds, value);
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
	 * 将 key 所储存的值加上增量 increment 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * @param key
	 * @param value
	 */
	public static Long incrBy(String key, long value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.incrBy(key, value);
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
	 * 将指定Key的Value原子性的递增1。如果该Key不存在，其初始值为0，在incr之后其值为1,返回递增后的值。
	 * @param key
	 */
	public static Long incr(String key) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.incr(key);
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
	 * 对 key 减去指定值 ， key 不存在时候会设置 key ，并认为原来的 value为0
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long decrBy(String key, long value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.decrBy(key,value);
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
	 * 对key的值做的是减减操作，decr一个不存在 key，则设置key为-1
	 * @param key
	 * @return
	 */
	public static Long decr(String key) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.decr(key);
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
	 * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
	 * 不存在的 key 当作空白字符串处理。
	 * @param key
	 * @param offset
	 * @param value
	 * @return 被 SETRANGE 修改之后，字符串的长度。
	 */
	public static Long setrange(String key, long offset, String value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.setrange(key, offset, value);
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
	 * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
	 * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getrange(String key, long start, long end) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.getrange(key, start, end);
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
	 * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
	 * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。
	 * 当 key 不存在时，自动生成一个新的字符串值。
	 * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
	 * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
	 * @param key
	 * @param offset
	 * @param value
	 * @return 指定偏移量原来储存的位
	 */
	public static Boolean setbit(String key, long offset, boolean value) {
		Boolean l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.setbit(key, offset, value);
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
	 * 返回 key 所储存的值的类型。
	 * @param key
	 * @return
	 * none (key不存在)
	 * string (字符串)
	 * list (列表)
	 * set (集合)
	 * zset (有序集)
	 * hash (哈希表)
	 */
	public static String type(String key) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.type(key);
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
	
	/*************************对Key及String的操作结束****************************/
	
	
	
	
	
	
	/*************************对Hash的操作开始****************************/

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
	 * 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public static Long hset(String key, String field, String value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hset(key, field, value);
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
	 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
	 * 若域 field 已经存在，该操作无效。
	 * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * 设置成功，返回 1 。
	 * 如果给定域已经存在且没有操作被执行，返回 0 。
	 */
	public static Long hsetnx(String key, String field, String value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hsetnx(key, field, value);
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
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
	 * 此命令会覆盖哈希表中已存在的域。
	 * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
	 * @param key
	 * @param map
	 * @return
	 * 如果命令执行成功，返回 OK 。
	 * 当 key 不是哈希表(hash)类型时，返回一个错误。
	 */
	public static String hmset(String key, Map<String, String> map) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hmset(key, map);
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
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param filed
	 * @return
	 */
	public static String hget(String key, String filed) {
		String l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hget(key, filed);
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
	 * 返回哈希表 key 中，一个或多个给定域的值。
	 * 如果给定的域不存在于哈希表，那么返回一个 nil 值。
	 * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
	 * @param key
	 * @param field
	 * @return
	 * 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	public static List<String> hmget(String key, String... field) {
		List<String> l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hmget(key, field);
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
	 * 
	 * 返回哈希表 key 中，所有的域和值。
	 * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
	 * @param key
	 * @return
	 */
	public static Map<String, String> hgetAll(String key) {
		Map<String, String> l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hgetAll(key);
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
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long hdel(String key, String... value) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hdel(key, value);
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
	 * 返回哈希表 key 中field的数量。
	 * @param key
	 * @return
	 * 哈希表中域的数量。当 key 不存在时，返回 0 。
	 */
	public static Long hlen(String key) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hlen(key);
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
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key, String field) {
		Boolean l = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hexists(key, field);
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
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。
	 * 增量也可以为负数，相当于对给定域进行减法操作。
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
	 * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
	 * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 
	 
	 * @param key
	 * @param field
	 * @param increment
	 * @return
	 */
	public static Long hincrBy(String key, String field, long increment) {
		Long l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hincrBy(key, field, increment);
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
		// 保证大于0--各库数据不一至可能会有问题。
		if (l < 0) {
			l = hset(key, field, "0");
		}
		return l;
	}

	/**
	 * 返回哈希表 key 中的所有域。
	 
	 
	 * @param key
	 * @return
	 * 一个包含哈希表中所有域的表。当 key 不存在时，返回一个空表。
	 */
	public static Set<String> hkeys(String key) {
		Set<String> l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hkeys(key);
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
	 * 返回哈希表 key 中所有域的值。
	 
	 
	 * @param key
	 * @return
	 * 一个包含哈希表中所有值的表。
	 * 当 key 不存在时，返回一个空表。
	 */
	public static List<String> hvals(String key) {
		List<String> l = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			l = jedis.hvals(key);
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
	/*************************对Hash的操作结束****************************/
	
}