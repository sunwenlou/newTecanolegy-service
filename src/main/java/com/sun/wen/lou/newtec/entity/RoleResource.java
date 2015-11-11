package com.sun.wen.lou.newtec.entity;

import java.io.Serializable;

public class RoleResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5664599257681333623L;
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getIsAvaliable() {
		return isAvaliable;
	}
	public void setIsAvaliable(int isAvaliable) {
		this.isAvaliable = isAvaliable;
	}
	private int roleId;
	private int resourceId;
	private int isAvaliable;
	
	

}
