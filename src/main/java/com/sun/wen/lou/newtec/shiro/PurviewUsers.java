package com.sun.wen.lou.newtec.shiro;

import java.io.Serializable;
import java.util.List;

/**
 * 用户权限信息
 */
public class PurviewUsers implements Serializable{
	
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
	 * 角色名称列表
	 */
	private List<String> roles;
	
	/**
	 * 权限名称列表
	 */
	private List<String> permissions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	
}