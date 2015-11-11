package com.sun.wen.lou.newtec.shiro;

import java.io.Serializable;

/**
 * 用户校验信息
 */
public class SecurityUsers implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String id;
		
	/**
	 * 用户登录名
	 */
	private String loginName;
	
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 是否删除 0:有效 1：删除
	 */
	private Integer delStatus;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
