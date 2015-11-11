package com.sun.wen.lou.newtec.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.wen.lou.newtec.entity.RedisConfig;
import com.sun.wen.lou.newtec.redis.RedisClient;
import com.sun.wen.lou.newtec.service.RedisService;
import com.sun.wen.lou.newtec.util.AESUtils;
import com.sun.wen.lou.newtec.util.HttpEntrance;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
	public static final String AES_UUC_KEY = "DtbW9aZurnFg3ZiP";

	@Autowired
	private RedisClient redisClient;

	public String queryResourceBysysFlagAndAccountId(String sysFlag,
			String accountId) {
		Map map = new HashMap();

		map.put("sysFlag", sysFlag);
		map.put("accountId", accountId);

		String resource = RedisClient.get(sysFlag + ":" + accountId);
		if ((resource != null) && (!"".equals(resource))) {
			return resource;
		}

		HttpEntrance.getInstance();
		resource = AESUtils.AES_Decrypt("DtbW9aZurnFg3ZiP",
				HttpEntrance.conn(RedisConfig.rbac_interface_resource, map));

		if ((resource != null) && (!"".equals(resource))) {
			RedisClient.set(sysFlag + ":" + accountId, resource);
		}
		return resource;
	}

	public String queryResourceBysysFlag(String sysFlag) {
		Map map = new HashMap();

		map.put("sysFlag", sysFlag);
		String resource = RedisClient.get(sysFlag);
		if ((resource != null) && (!"".equals(resource))) {
			return resource;
		}

		HttpEntrance.getInstance();
		resource = AESUtils.AES_Decrypt("DtbW9aZurnFg3ZiP",
				HttpEntrance.conn(RedisConfig.rbac_interface_resourceAll, map));

		if ((resource != null) && (!"".equals(resource))) {
			RedisClient.set(sysFlag, resource);
		}
		return resource;
	}

	public String queryPermissionsBysysFlagAndAccountId(String sysFlag,
			String accountId) {
		Map map = new HashMap();

		map.put("sysFlag", sysFlag);
		map.put("accountId", accountId);

		String resource = RedisClient.get(sysFlag + ":" + accountId);
		if ((resource != null) && (!"".equals(resource))) {
			return resource;
		}

		HttpEntrance.getInstance();
		resource = AESUtils.AES_Decrypt("DtbW9aZurnFg3ZiP",
				HttpEntrance.conn(RedisConfig.rbac_interface_permissions, map));

		if ((resource != null) && (!"".equals(resource))) {
			RedisClient.set(sysFlag + ":permissions:" + accountId, resource);
		}
		return resource;
	}
}