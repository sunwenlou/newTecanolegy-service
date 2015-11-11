package com.sun.wen.lou.newtec.service;

public abstract interface RedisService {
	public abstract String queryResourceBysysFlagAndAccountId(
			String paramString1, String paramString2);

	public abstract String queryResourceBysysFlag(String paramString);

	public abstract String queryPermissionsBysysFlagAndAccountId(
			String paramString1, String paramString2);
}
