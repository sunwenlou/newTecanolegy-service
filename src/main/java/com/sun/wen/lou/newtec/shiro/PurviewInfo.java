package com.sun.wen.lou.newtec.shiro;

import java.io.Serializable;

public class PurviewInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 资源标识
	 */
	private String optSign;
	
	/**
	 * 资源路径
	 */
	private String optPath;

	public String getOptSign() {
		return optSign;
	}

	public void setOptSign(String optSign) {
		this.optSign = optSign;
	}

	public String getOptPath() {
		return optPath;
	}

	public void setOptPath(String optPath) {
		this.optPath = optPath;
	}
	
}