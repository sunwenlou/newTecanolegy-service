package com.sun.wen.lou.newtec.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8237298683722161126L;
	private int userId;
	private int roleId;
	private int isAvaliable;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getIsAvaliable() {
		return isAvaliable;
	}
	public void setIsAvaliable(int isAvaliable) {
		this.isAvaliable = isAvaliable;
	}
	
	

}
